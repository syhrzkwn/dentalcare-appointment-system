# Deployment

How the app gets from GitHub to the production server, and how to look after that server.
For Docker basics, read [docker-guide.md](docker-guide.md) first.

`<server-ip>` stands for the server's Elastic IP. The real value is kept out of this public repo: it is in the `SSH_HOST` secret and in your `~/.ssh/config` entry for `syhrzkwn-dev-my-server-1`.

---

## 1. Overview

```
 pull request into master ──► CI (build + smoke test), on every new commit to the PR
        │
        └── merge ──► Deploy workflow
                        1. build image → ghcr.io/syhrzkwn/dentalcare-appointment-system
                        2. SSH to the server as "deploy"
                        3. server pulls the image and restarts the app
                        4. server checks the app responds
```

Only **merged pull requests** deploy. Pushing to `develop` runs nothing, and a direct push to `master` runs nothing and is **not deployed**, so the server keeps running the last merged version.

CI does not run again after the merge. To make sure the merged code is exactly the code CI tested, protect `master` (section 8).

| Piece | Where |
|---|---|
| CI workflow | `.github/workflows/ci.yml`. Runs on pull requests into `master`: when opened or reopened, and on every new commit pushed to them. |
| Deploy workflow | `.github/workflows/deploy.yml`. Runs when a pull request into `master` is merged. |
| Smoke test | `.github/scripts/smoke-test.sh`. Checks pages load, the seeded admin can log in, and the dashboard can query the database. Also works locally: `.github/scripts/smoke-test.sh http://localhost:8080` |
| Production compose file | `docker-compose.prod.yml`. Copied to the server by the deploy script on every deploy. |
| Server deploy script | `deploy/dentalcare-deploy.sh`, installed on the server as `/usr/local/bin/dentalcare-deploy`. |
| Server | AWS EC2 t3.small, Ubuntu 26.04, with an Elastic IP (`<server-ip>` below). App files in `/opt/dentalcare`. |

---

## 2. The three credentials

| Credential | Used by | To get into | Lives in |
|---|---|---|---|
| `syhrzkwn-dev-my-server-1.pem` | You | The server, as `ubuntu` (admin, has `sudo`) | Your Mac only (`~/.ssh/`) |
| GitHub token | You | GitHub, for `git push` | macOS Keychain |
| `dentalcare-deploy` key | GitHub Actions | The server, as `deploy` (no `sudo`) | GitHub secret `SSH_PRIVATE_KEY` (backup in `~/.ssh/dentalcare-deploy`) |

The deploy key is **restricted**: on the server it can only run the deploy script, nothing else. No shell, no file copying, no other commands. If it ever leaks, the worst someone can do is redeploy a commit that is already on `master`.

---

## 3. GitHub secrets

Repository → Settings → Secrets and variables → Actions:

| Secret | Value |
|---|---|
| `SSH_HOST` | `<server-ip>` |
| `SSH_USER` | `deploy` |
| `SSH_PRIVATE_KEY` | Contents of `~/.ssh/dentalcare-deploy` (the private key, including the `BEGIN`/`END` lines) |
| `SSH_KNOWN_HOSTS` | The server's public host key, one line: the output of `ssh-keyscan -t ed25519 <server-ip>`. Check its fingerprint matches the server's before saving it. |

The deploy workflow also uses `GITHUB_TOKEN`, which GitHub creates automatically for each run. It is used to push the image to ghcr.io and is passed to the server just long enough to pull it.

---

## 4. What the deploy script does

`/usr/local/bin/dentalcare-deploy` runs on the server whenever the deploy key connects. It:

1. Accepts only `deploy <commit-sha> <github-user>` and refuses anything else.
2. Asks GitHub whether the commit is part of `master`, and refuses commits from other branches or forks.
3. Downloads `docker-compose.prod.yml` from that exact commit into `/opt/dentalcare/docker-compose.yml`.
4. Logs in to ghcr.io with the token from the workflow, pulls the app image `sha-<commit>`, and logs out.
5. Restarts the app with `docker compose up -d`. The database keeps running, and its data stays in the `db-data` volume.
6. Waits up to 2 minutes for the app to answer on port 80. On success it removes old images; otherwise it prints the app logs and the deploy fails.

The deployed commit is recorded in `/opt/dentalcare/DEPLOYED_COMMIT`.

### Updating the deploy script

The script is owned by root, so the deploy key can't change it, and changes in the repo do **not** reach the server automatically. After editing `deploy/dentalcare-deploy.sh`, install it by hand:

```sh
scp deploy/dentalcare-deploy.sh syhrzkwn-dev-my-server-1:/tmp/dentalcare-deploy
ssh syhrzkwn-dev-my-server-1 'sudo install -o root -g root -m 755 /tmp/dentalcare-deploy /usr/local/bin/dentalcare-deploy && rm /tmp/dentalcare-deploy'
```

---

## 5. One-time server setup (already done)

Kept here so the server can be rebuilt. Run as `ubuntu`:

1. Update the system and add 2 GB of swap (listed in `/etc/fstab`).
2. Install Docker Engine and the Compose plugin from Docker's apt repository, with log rotation in `/etc/docker/daemon.json`:
   ```json
   { "log-driver": "json-file", "log-opts": { "max-size": "10m", "max-file": "3" } }
   ```
3. Create the `deploy` user (no password, no `sudo`, member of the `docker` group):
   ```sh
   sudo adduser --disabled-password --gecos "" deploy
   sudo usermod -aG docker deploy
   ```
4. Add the deploy key to `/home/deploy/.ssh/authorized_keys`, restricted to the script:
   ```
   restrict,command="/usr/local/bin/dentalcare-deploy" ssh-ed25519 AAAA… github-actions-deploy@dentalcare
   ```
5. Install the deploy script (section 4).
6. Create `/opt/dentalcare` (owner `deploy`, mode `750`) containing:
   - `sql/schema.sql` and `sql/seed.sql`, used only when the database is first created
   - `.env` (mode `600`) with generated passwords:
     ```
     MYSQL_ROOT_PASSWORD=<random>
     DB_USER=dentalcare
     DB_PASSWORD=<random>
     ```
7. AWS Security Group: allow `22`, `80` and `443` inbound. Never open `3306` or `8080`.

If the server is rebuilt, its SSH host key changes, so update the `SSH_KNOWN_HOSTS` secret:
```sh
ssh-keyscan -t ed25519 <server-ip>
```

---

## 6. Day-to-day

Log in as admin with `ssh syhrzkwn-dev-my-server-1`. The app runs as `deploy`, so switch user first:

```sh
sudo -iu deploy
cd /opt/dentalcare
```

| Goal | Command (as `deploy`, in `/opt/dentalcare`) |
|---|---|
| What is running | `docker compose ps` |
| Which commit is live | `cat DEPLOYED_COMMIT` |
| App logs | `docker compose logs -f app` |
| Database logs | `docker compose logs -f db` |
| Restart the app | `docker compose restart app` |
| MySQL prompt | `docker compose exec db sh -c 'mysql -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" dentalcare'` |

Never run `docker compose down -v` on the server. `-v` deletes the database.

### Rolling back

Revert the bad change through a pull request (for example with `git revert` on a branch, then a PR into `master`). Merging it deploys the previous working code, the same way as any other change.

### Deploying again without a code change

To redeploy, for example after rebuilding the server, open the last successful **Deploy** run in the Actions tab and click **Re-run all jobs**.

### Backing up the database

The server's disk holds the only copy of the data. To take a backup (as `deploy`, in `/opt/dentalcare`):

```sh
docker compose exec -T db sh -c 'mysqldump -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" --single-transaction dentalcare' > backup-$(date +%F).sql
```

Copy backups off the server, for example with `scp`, so they survive if the server is lost.

### Changing the database schema

`sql/schema.sql` only runs on an empty database, so changes to it do **not** reach the existing production database. Apply table changes by hand with SQL at the MySQL prompt (take a backup first), and update `sql/schema.sql` too so new setups match.

---

## 7. Still to do

- **Open port 80** (and later 443) in the Security Group, so the site can be reached at `http://<server-ip>`.
- **Domain and HTTPS**: point an `A` record at `<server-ip>`, then add a reverse proxy (for example Caddy) in front of the app for automatic HTTPS certificates.
- **Set a strong admin password** right after the first deploy.

---

## 8. Protecting `master`

CI only runs on the pull request, so GitHub has to guarantee that what gets merged is what CI tested. In **Settings → Branches → Add branch ruleset** (or *Add rule*) for `master`, turn on:

- **Require a pull request before merging**: blocks direct pushes, which would otherwise land on `master` without being tested or deployed.
- **Require status checks to pass**: select **Build WAR** and **Docker smoke test**. They appear in the list after CI has run on a pull request at least once.
- **Require branches to be up to date before merging**: if `master` changed since CI ran, GitHub makes you update the pull request and run CI again first.
- **Do not allow bypassing the above settings**: otherwise the repository owner can still skip these rules.
