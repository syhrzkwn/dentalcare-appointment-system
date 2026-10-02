#!/bin/sh
# Creates the first admin account from ADMIN_EMAIL and ADMIN_PASSWORD (set in .env), so no
# admin password is stored in the repo. MySQL runs this once, when it initialises an empty
# database (files in /docker-entrypoint-initdb.d run in alphabetical order, after schema.sql).
set -eu

: "${ADMIN_EMAIL:?set ADMIN_EMAIL in .env}"
: "${ADMIN_PASSWORD:?set ADMIN_PASSWORD in .env}"

# The app stores MD5 password hashes; hash here so the password itself never appears in SQL
hash=$(printf '%s' "$ADMIN_PASSWORD" | md5sum | cut -d' ' -f1)
email=$(printf '%s' "$ADMIN_EMAIL" | sed "s/'/''/g")

MYSQL_PWD="$MYSQL_ROOT_PASSWORD" mysql -uroot dentalcare <<SQL
INSERT INTO staffs (staff_firstname, staff_lastname, staff_phone, staff_email, staff_password)
VALUES ('Admin', '', '', '$email', '$hash');
SQL
echo "seed-admin.sh: created admin account $ADMIN_EMAIL"
