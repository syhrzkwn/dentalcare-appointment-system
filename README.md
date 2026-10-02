# dentalcare-appointment-system
Dentalcare Appointment System is develop on top of Java Web (JSP, JSTL, and Servlet). It was a group assignment project for CSC584 - Enterprise Programming.

## Run with Docker
```sh
cp .env.example .env      # then set the passwords in .env
docker compose up --build
```
Then open http://localhost:8080/

This starts MySQL 8.4 (loaded with `sql/schema.sql` and `sql/seed.sql` on first start) and the app on Tomcat 9.
Data is kept in the `db-data` volume; run `docker compose down -v` to wipe it and re-run the SQL scripts.
Set `APP_PORT` or `DB_PORT` in `.env` if 8080 or 3306 is already taken.

Admin login: http://localhost:8080/admin/login.jsp?secret_key=dn3@ZDt8UJ8l with the admin account from `sql/seed.sql`.

New to Docker? See [docs/docker-guide.md](docs/docker-guide.md) for a step-by-step explanation of the setup.

## Run without Docker
Requirements:
- JDK 8 or newer
- Maven 3.6+
- Apache Tomcat 9 (Tomcat 10+ uses the `jakarta.*` namespace and will not run this app)
- MySQL 8 with `sql/schema.sql` then `sql/seed.sql` loaded

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
