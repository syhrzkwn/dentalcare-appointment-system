#!/usr/bin/env bash
# Smoke test for a running stack: pages load and the seeded admin can log in and open
# a page that queries the database.
# Usage: .github/scripts/smoke-test.sh [base-url]   (default: http://localhost:8080)
set -euo pipefail

BASE_URL="${1:-http://localhost:8080}"
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

curl -s -c "$COOKIES" -b "$COOKIES" \
    --data-urlencode email=admin@dentalcare.com \
    --data-urlencode "password=$ADMIN_PASSWORD" \
    --data-urlencode 'user_type=08y*6M' \
    "$BASE_URL/auth_login.do" | grep -q 'login successfully' || fail "admin login failed"
echo "ok  admin login"

code=$(curl -s -b "$COOKIES" -o /dev/null -w '%{http_code}' "$BASE_URL/admin/dashboard.jsp")
[ "$code" = 200 ] || fail "admin dashboard returned $code"
echo "ok  admin dashboard (database queries)"

echo "Smoke test passed"
