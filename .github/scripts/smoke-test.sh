#!/usr/bin/env bash
# Smoke test for a running stack: pages load, the admin login page only opens with the secret
# key, and the admin account can log in and open a page that queries the database.
# Usage: ADMIN_EMAIL=... ADMIN_PASSWORD=... ADMIN_SECRET_KEY=... .github/scripts/smoke-test.sh [base-url]
#   the three values are the ones from .env (default url: http://localhost:8080)
set -euo pipefail

BASE_URL="${1:-http://localhost:8080}"
: "${ADMIN_EMAIL:?set ADMIN_EMAIL to the admin login email}"
: "${ADMIN_PASSWORD:?set ADMIN_PASSWORD to the admin login password}"
: "${ADMIN_SECRET_KEY:?set ADMIN_SECRET_KEY to the admin login page key}"
COOKIES="$(mktemp)"
trap 'rm -f "$COOKIES"' EXIT

fail() { echo "FAIL: $*" >&2; exit 1; }

echo "Waiting for $BASE_URL ..."
for i in $(seq 1 60); do
    curl -fsS -o /dev/null "$BASE_URL/" && break
    [ "$i" -eq 60 ] && fail "app did not respond within 2 minutes"
    sleep 2
done

for page in "" login.jsp signup.jsp css/main.css; do
    code=$(curl -s -o /dev/null -w '%{http_code}' "$BASE_URL/$page")
    [ "$code" = 200 ] || fail "GET /$page returned $code"
    echo "ok  GET /$page"
done

curl -s -G --data-urlencode "secret_key=$ADMIN_SECRET_KEY" "$BASE_URL/admin/login.jsp" \
    | grep -q '<title>Admin - Login</title>' || fail "admin login page did not open with the secret key"
echo "ok  admin login page opens with the secret key"
curl -s "$BASE_URL/admin/login.jsp?secret_key=wrong-key" \
    | grep -q '<title>Admin - Login</title>' && fail "admin login page opened with a wrong key"
echo "ok  admin login page stays closed with a wrong key"

curl -s -c "$COOKIES" -b "$COOKIES" \
    --data-urlencode "email=$ADMIN_EMAIL" \
    --data-urlencode "password=$ADMIN_PASSWORD" \
    --data-urlencode 'user_type=08y*6M' \
    "$BASE_URL/auth_login.do" | grep -q 'login successfully' || fail "admin login failed"
echo "ok  admin login"

code=$(curl -s -b "$COOKIES" -o /dev/null -w '%{http_code}' "$BASE_URL/admin/dashboard.jsp")
[ "$code" = 200 ] || fail "admin dashboard returned $code"
echo "ok  admin dashboard (database queries)"

echo "Smoke test passed"
