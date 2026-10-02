# dentalcare-appointment-system
Dentalcare Appointment System is develop on top of Java Web (JSP, JSTL, and Servlet). It was a group assignment project for CSC584 - Enterprise Programming.

## Run with Docker
```sh
cp .env.example .env      # then set the passwords in .env, including ADMIN_PASSWORD
docker compose up --build
```
Then open http://localhost:8080/

This starts MySQL 8.4 (loaded with `sql/schema.sql` and `sql/seed.sql` on first start, plus an admin account from `ADMIN_EMAIL` / `ADMIN_PASSWORD` in `.env`) and the app on Tomcat 9.
Data is kept in the `db-data` volume; run `docker compose down -v` to wipe it and re-run the SQL scripts.
Set `APP_PORT` or `DB_PORT` in `.env` if 8080 or 3306 is already taken.

The admin panel is at `/admin/login.jsp?secret_key=<key>` (the key is in `web/admin/login.jsp`); log in with `ADMIN_EMAIL` and `ADMIN_PASSWORD` from your `.env`.

New to Docker? See [docs/docker-guide.md](docs/docker-guide.md) for a step-by-step explanation of the setup.

## Run without Docker
Requirements:
- JDK 8 or newer
- Maven 3.6+
- Apache Tomcat 9 (Tomcat 10+ uses the `jakarta.*` namespace and will not run this app)
- MySQL 8 with `sql/schema.sql` then `sql/seed.sql` loaded, plus an admin account:
  ```sql
  INSERT INTO staffs (staff_firstname, staff_lastname, staff_phone, staff_email, staff_password)
  VALUES ('Admin', '', '', 'you@example.com', MD5('your-admin-password'));
  ```

Give Tomcat the database credentials by creating `$CATALINA_HOME/bin/setenv.sh`:
```sh
export DB_USER=your_db_user
export DB_PASSWORD=your_db_password
```

```sh
mvn package
cp target/dentalcare-appointment-system.war $CATALINA_HOME/webapps/
$CATALINA_HOME/bin/startup.sh
```
Then open http://localhost:8080/dentalcare-appointment-system/

JSTL and the MySQL driver are bundled in the WAR, so no extra jars need to be added to Tomcat.

## Database settings
The app reads these environment variables:

| Variable | Default |
|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3306/dentalcare` |
| `DB_USER` | none, required |
| `DB_PASSWORD` | none, required |

With Docker, `docker-compose.yml` sets them from `.env`.

## CI/CD
Live at https://dentalcare.syhrzkwn.dev.

Pull requests into `master` are built and smoke-tested by GitHub Actions.
Merging a pull request into `master` builds the Docker image and deploys it to the production server; direct pushes to `master` are not deployed.
See [docs/deployment.md](docs/deployment.md).
