#!/usr/bin/env bash
# Forced command for the GitHub Actions deploy key. See docs/deployment.md.
#
# Installed on the server as /usr/local/bin/dentalcare-deploy (owned by root) and tied to the
# key in /home/deploy/.ssh/authorized_keys, so that key can run nothing but this script.
#
# Usage over SSH:  deploy <commit-sha> <github-user>
# stdin:           a token that can read the image on ghcr.io (the workflow's GITHUB_TOKEN)
set -euo pipefail

REPO="syhrzkwn/dentalcare-appointment-system"
APP_DIR="/opt/dentalcare"

fail() { echo "deploy: $*" >&2; exit 1; }

read -r action sha user extra <<<"${SSH_ORIGINAL_COMMAND:-}" || true
[[ "${action:-}" == "deploy" && "${sha:-}" =~ ^[0-9a-f]{40}$ && "${user:-}" =~ ^[A-Za-z0-9-]{1,39}$ && -z "${extra:-}" ]] \
    || fail "usage: deploy <40-character commit sha> <github-user>"

# Only commits that are part of master can be deployed (rejects other branches and forks)
compare=$(curl -fsS "https://api.github.com/repos/$REPO/compare/$sha...master" 2>/dev/null) \
    || fail "commit $sha not found on GitHub"
status=$(python3 -c 'import json, sys; print(json.load(sys.stdin)["status"])' <<<"$compare")
[[ "$status" == "identical" || "$status" == "ahead" ]] || fail "commit $sha is not on master"

cd "$APP_DIR"

# Use the compose file from the same commit as the image
curl -fsS -o docker-compose.yml.new "https://raw.githubusercontent.com/$REPO/$sha/docker-compose.prod.yml" \
    || fail "cannot download docker-compose.prod.yml for $sha"
mv docker-compose.yml.new docker-compose.yml

# Pull with the workflow's short-lived token, then log out again
IFS= read -r token || fail "no registry token on stdin"
trap 'docker logout ghcr.io >/dev/null 2>&1 || true' EXIT
printf '%s\n' "$token" | docker login ghcr.io -u "$user" --password-stdin >/dev/null 2>&1 \
    || fail "cannot log in to ghcr.io"
export IMAGE_TAG="sha-$sha"
docker compose pull --quiet app
docker logout ghcr.io >/dev/null 2>&1 || true

docker compose up -d --remove-orphans
echo "$sha" > DEPLOYED_COMMIT

# Tomcat takes a few seconds to deploy the WAR
for _ in $(seq 1 60); do
    if curl -fsS -o /dev/null http://127.0.0.1/; then
        docker image prune -af >/dev/null
        echo "deploy: $sha is live"
        exit 0
    fi
    sleep 2
done
docker compose logs --tail 50 app >&2
fail "app did not respond after deploying $sha"
