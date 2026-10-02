# Docker, explained for this project

This guide explains what Docker is, what each Docker file in this repository does, and how to use it day to day. It assumes no prior Docker knowledge.

---

## 1. Why Docker?

To run this app the normal way, your computer needs all of this installed and configured correctly:

- Java (JDK)
- Maven, to compile the code into a `.war` file
- Apache Tomcat **9** (not 10 or later), to run the `.war`
- MySQL 8, with the right database, user and password
- The tables from `sql/schema.sql` and the starting data from `sql/seed.sql`

That is a lot of setup, and it is easy to get one piece wrong, such as the wrong Tomcat version or a forgotten seed script.

**Docker packages all of that for you.** With Docker installed, the whole system starts with one command:

```sh
docker compose up --build
```

The only thing you install is Docker itself. Java, Maven, Tomcat and MySQL run inside Docker, so they are never installed on your Mac.

---

## 2. The five words you need to know

| Word | What it means | Analogy |
|---|---|---|
| **Image** | A read-only package containing an operating system, programs and files, ready to run. | A recipe, or an installer. |
| **Container** | A running copy of an image. It behaves like a small, isolated computer. | The dish cooked from the recipe. You can cook many from one recipe. |
| **Dockerfile** | A text file with step-by-step instructions for **building an image**. | The recipe card you write yourself. |
| **Volume** | Storage that lives outside a container, so data survives when the container is deleted. | An external hard drive plugged into the container. |
| **Docker Compose** | A tool that starts **several containers together** from one file (`docker-compose.yml`) and connects them. | A conductor telling each musician when to start. |

Some other terms:

- **Docker Hub**: the public online library of ready-made images (`mysql`, `tomcat`, `maven` and so on). Docker downloads images from there automatically.
- **Port mapping**: containers are isolated, so to open the app in your browser, Docker forwards a port on your Mac (for example `8080`) to a port inside the container.

---

## 3. The big picture

When you run `docker compose up --build`, this is what exists:

```
 Your Mac
 ┌────────────────────────────────────────────────────────────────┐
 │                                                                │
 │   Browser ──► localhost:8080                localhost:3306     │
 │                    │                              │            │
 │   ┌────────────────┼──────── Docker ──────────────┼─────────┐  │
 │   │                ▼                              ▼         │  │
 │   │   ┌──────────────────────┐       ┌──────────────────┐   │  │
 │   │   │  container "app"     │       │  container "db"  │   │  │
 │   │   │  Tomcat 9 + your app │──────►│  MySQL 8.4       │   │  │
 │   │   │  (port 8080)         │  db:  │  (port 3306)     │   │  │
 │   │   └──────────────────────┘  3306 └────────┬─────────┘   │  │
 │   │                                           │             │  │
 │   │                                  volume "db-data"       │  │
 │   │                                  (database files)       │  │
 │   └─────────────────────────────────────────────────────────┘  │
 └────────────────────────────────────────────────────────────────┘
```

- There are **two containers**: `app` (your website on Tomcat) and `db` (the MySQL database).
- Inside Docker, the app reaches the database using the name **`db`**, the same way a website has a hostname.
- The database files are kept in a **volume** called `db-data`, so your data is not lost when containers stop.

---

## 4. The files I added

These files were added at the root of the project:

| File | Purpose |
|---|---|
| `Dockerfile` | Instructions to build the **app** image (compile the code, then put it in Tomcat). |
| `docker-compose.yml` | Starts the **app** and **db** containers together and connects them. |
| `.dockerignore` | A list of files Docker should not copy into the image. |
| `.env.example` | A template for your `.env` file, which holds the database passwords (section 6). |

I also changed the code so the app can be told where the database is (section 7).

---

## 5. `Dockerfile`, line by line

```dockerfile
# Build the WAR
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src src
COPY web web
RUN mvn -B -q package

# Run on Tomcat 9 (javax.servlet); deployed as ROOT so the app is served at /
FROM tomcat:9.0-jdk21-temurin
COPY --from=build /app/target/dentalcare-appointment-system.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
```

This is a **multi-stage build**: there are two `FROM` lines, so two stages.

### Stage 1: compile the code (the "build" stage)

| Line | What it does |
|---|---|
| `FROM maven:3.9-eclipse-temurin-21 AS build` | Start from an image that already has **Maven 3.9** and **Java 21**. Name this stage `build`. |
| `WORKDIR /app` | Inside this temporary machine, work in the folder `/app` (it is created if missing). Like `cd /app`. |
| `COPY pom.xml .` | Copy `pom.xml` from your project into `/app`. |
| `RUN mvn -B -q dependency:go-offline` | Download all the libraries listed in `pom.xml` (JSTL, the MySQL driver and so on). |
| `COPY src src` and `COPY web web` | Copy your Java source code and your JSP/CSS/images into the image. |
| `RUN mvn -B -q package` | Compile everything and produce `target/dentalcare-appointment-system.war`. |

**Why copy `pom.xml` first and the code later?** Docker caches each step. If you only edit a `.java` or `.jsp` file, `pom.xml` hasn't changed, so Docker reuses the cached "download libraries" step instead of downloading everything again. Rebuilds are much faster.

### Stage 2: the image that actually runs

| Line | What it does |
|---|---|
| `FROM tomcat:9.0-jdk21-temurin` | Start fresh from an image with **Tomcat 9** and Java 21 already installed. |
| `COPY --from=build ... ROOT.war` | Take **only the finished `.war` file** from stage 1 and put it in Tomcat's `webapps` folder. It is named `ROOT.war`, which makes Tomcat serve the app at `http://localhost:8080/` instead of `http://localhost:8080/dentalcare-appointment-system/`. |
| `EXPOSE 8080` | Documents that Tomcat listens on port 8080 inside the container. |

**Why two stages?** Maven and the source code are only needed to compile. The final image keeps just Tomcat and the `.war`, so it is smaller and contains nothing it doesn't need.

There is no "start" command at the end because the official Tomcat image already starts Tomcat by default.

---

## 6. `docker-compose.yml`, section by section

### Passwords live in `.env`, not in this file

`docker-compose.yml` is committed to Git, and the repository is public, so it must not contain passwords. Instead it reads them from a file called **`.env`** in the project folder. Docker Compose loads `.env` automatically, and `.gitignore` keeps it out of Git, so your passwords stay on your machine.

Create it once from the template and fill in the passwords:

```sh
cp .env.example .env
```

```ini
MYSQL_ROOT_PASSWORD=pick-a-strong-password
DB_USER=dentalcare
DB_PASSWORD=pick-another-strong-password
ADMIN_EMAIL=admin@dentalcare.com
ADMIN_PASSWORD=pick-your-admin-login-password
DB_PORT=3306
APP_PORT=8080
```

In `docker-compose.yml`, `${DB_PASSWORD}` means "the value of `DB_PASSWORD` from `.env`". The form `${DB_PASSWORD:?set DB_PASSWORD in .env}` adds a safety check: if the value is missing, Compose stops and prints that message instead of starting with an empty password.

### The `db` service (MySQL)

```yaml
  db:
    image: mysql:8.4
```
Use the official **MySQL 8.4** image from Docker Hub. Nothing to build; Docker just downloads it.

```yaml
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD:?set MYSQL_ROOT_PASSWORD in .env}
      MYSQL_DATABASE: dentalcare
      MYSQL_USER: ${DB_USER:?set DB_USER in .env}
      MYSQL_PASSWORD: ${DB_PASSWORD:?set DB_PASSWORD in .env}
```
Settings the MySQL image reads on its **first start**:
- Set the `root` (admin) password to `MYSQL_ROOT_PASSWORD` from `.env`.
- Create a database called `dentalcare`.
- Create the user `DB_USER` with the password `DB_PASSWORD`, allowed to use that database.

> Because these are only read on the first start, changing the passwords in `.env` later does not change them in an existing database. Either reset the database (section 9) or change them with SQL (`ALTER USER`).

```yaml
    ports:
      - "127.0.0.1:${DB_PORT:-3306}:3306"
```
Port mapping, in the form **`address : your-Mac-port : container-port`**. This lets you connect to the database from your Mac (for example with TablePlus at `127.0.0.1:3306`).
- `127.0.0.1` means **only your own Mac** can connect. Without it, Docker would open the port to every device on your Wi-Fi network.
- `${DB_PORT:-3306}` means "use `DB_PORT` from `.env` if set, otherwise 3306". If you already run MySQL on your Mac, port 3306 is taken, so set `DB_PORT=3307` in `.env`.

```yaml
    volumes:
      - db-data:/var/lib/mysql
      - ./sql:/docker-entrypoint-initdb.d:ro
```
- `db-data:/var/lib/mysql`: MySQL keeps its data files in `/var/lib/mysql`. Storing that folder in the `db-data` volume means **your data survives** stopping and restarting.
- `./sql:/docker-entrypoint-initdb.d:ro`: this makes your project's `sql/` folder visible inside the container (`ro` = read-only). The MySQL image automatically runs every `.sql` and `.sh` file in `/docker-entrypoint-initdb.d`, in alphabetical order: `schema.sql` creates the tables, `seed-admin.sh` creates the admin account from `ADMIN_EMAIL` / `ADMIN_PASSWORD`, and `seed.sql` adds the starting data. **This only happens the very first time, when the database is empty.** See section 9.

```yaml
    healthcheck:
      test: ["CMD-SHELL", "mysqladmin ping -h 127.0.0.1 -uroot -p\"$$MYSQL_ROOT_PASSWORD\""]
      interval: 5s
      timeout: 5s
      retries: 30
```
MySQL takes several seconds to start, and longer on the first run while it creates the database and runs the SQL scripts. This tells Docker how to check that it is **really ready**: run `mysqladmin ping` every 5 seconds, up to 30 times. Until the check passes, the container is "starting"; afterwards it is "healthy".
`$$MYSQL_ROOT_PASSWORD` reads the password from inside the container when the check runs, so it is never written in this file. The double `$$` tells Compose not to replace it itself.

### The `app` service (your website)

```yaml
  app:
    build: .
```
Instead of downloading an image, **build** one from the `Dockerfile` in this folder (`.` = current folder).

```yaml
    environment:
      DB_URL: jdbc:mysql://db:3306/dentalcare
      DB_USER: ${DB_USER:?set DB_USER in .env}
      DB_PASSWORD: ${DB_PASSWORD:?set DB_PASSWORD in .env}
```
Tells the app where the database is and how to log in, using the same user and password from `.env` that MySQL was set up with. Note the host is **`db`**, the name of the other service, not `localhost`. Inside a container, `localhost` means "this container itself", so the app reaches MySQL by its service name. Compose creates a private network where each service name works as a hostname.

```yaml
    ports:
      - "${APP_PORT:-8080}:8080"
```
Makes Tomcat reachable from your browser at `http://localhost:8080`. If port 8080 is busy, set `APP_PORT=9090` in `.env` and open `http://localhost:9090`.

```yaml
    depends_on:
      db:
        condition: service_healthy
```
**Don't start the app until the database is healthy.** Without this, the app could start first and fail to connect.

### The `volumes` section at the bottom

```yaml
volumes:
  db-data:
```
Declares the named volume `db-data` used above. Docker creates and manages it; you don't need to know where it is on disk.

---

## 7. How the app knows where the database is

Before, the database address was written directly into the code in 4 places, pointing at `localhost`, which doesn't work inside Docker.

Now there is one place: `src/java/com/dentalcare/util/DBConnection.java` reads three **environment variables**:

| Variable | Value in Docker (set in `docker-compose.yml`) | Default when not set |
|---|---|---|
| `DB_URL` | `jdbc:mysql://db:3306/dentalcare` | `jdbc:mysql://localhost:3306/dentalcare` |
| `DB_USER` | `DB_USER` from `.env` | none, so the connection fails |
| `DB_PASSWORD` | `DB_PASSWORD` from `.env` | none, so the connection fails |

The user name and password deliberately have no default in the code, so no password is ever written in the source.

The three `head.jsp` files (used by the JSTL `<sql:...>` tags in the pages) take their settings from `DBConnection` too, so everything uses the same configuration.

An **environment variable** is a named setting given to a program when it starts, outside the code. Docker Compose sets them for the container, so the same code works both inside Docker (`db`) and outside Docker (`localhost`) without changes.

---

## 8. `.dockerignore`

```
.git
.DS_Store
target
.env
```
When Docker builds an image, it first sends your project folder to the Docker engine. This file lists things to leave out:
- `.git`: the Git history, which is large and not needed to build.
- `.DS_Store`: macOS Finder junk.
- `target`: old local build output; the image always builds fresh.
- `.env`: your passwords, which must never be baked into an image.

It works like `.gitignore`, but for Docker.

---

## 9. What happens when you run `docker compose up --build`

0. **Read `.env`**: Compose fills in the passwords and ports. If `.env` is missing or a password is empty, it stops here with a message like `set DB_PASSWORD in .env`.
1. **Build the app image**: Docker follows the `Dockerfile` (downloads Maven/Java, compiles the code, puts the `.war` into Tomcat). The first build takes a few minutes; later builds are faster thanks to caching.
2. **Download MySQL**: the `mysql:8.4` image is downloaded the first time only.
3. **Create the network and volume**: a private network for the two containers, plus the `db-data` volume if it doesn't exist.
4. **Start `db`**: if the `db-data` volume is **empty** (first run), MySQL creates the `dentalcare` database and user, then runs `sql/schema.sql`, `sql/seed-admin.sh` (your admin account) and `sql/seed.sql`.
5. **Wait**: Docker runs the health check until MySQL answers.
6. **Start `app`**: Tomcat starts and deploys your app.
7. **Open** http://localhost:8080/

**Important: the SQL scripts run only once.** On the next start the volume already contains a database, so the scripts are skipped and your data is kept. If you change `schema.sql` or `seed.sql` and want them applied, reset the database:

```sh
docker compose down -v      # -v also deletes the db-data volume (ALL data is lost)
docker compose up --build   # starts fresh and runs the SQL scripts again
```

---

## 10. Everyday commands

Run these in the project folder (where `docker-compose.yml` is).

| Goal | Command |
|---|---|
| Start everything (and rebuild the app) | `docker compose up --build` |
| Start in the background (terminal stays free) | `docker compose up --build -d` |
| See what is running | `docker compose ps` |
| See the app's logs (Tomcat output, errors) | `docker compose logs -f app` (Ctrl+C to stop watching) |
| See the database logs | `docker compose logs -f db` |
| Stop everything (data is kept) | `docker compose down`, or Ctrl+C if running in the foreground |
| Stop and **delete all database data** | `docker compose down -v` |
| Open a MySQL prompt inside the db container | `docker compose exec db sh -c 'mysql -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" dentalcare'` |

### After you change the code

The container runs a **copy** of your code made at build time, so edits to `.java` or `.jsp` files do **not** appear automatically. Rebuild:

```sh
docker compose up --build
```

### Logging in

- Website: http://localhost:8080/
- Admin login: http://localhost:8080/admin/login.jsp?secret_key=<key> (the key is in `web/admin/login.jsp`),
  with `ADMIN_EMAIL` / `ADMIN_PASSWORD` from your `.env`. They only take effect when the database is first created; after that, change the password on the admin Account page.

---

## 11. Troubleshooting

**`Cannot connect to the Docker daemon` / `failed to connect to the docker API`**
Docker Desktop isn't running. Open the Docker app and wait until it says it's running, then try again.

**`port is already allocated` / `address already in use`**
Something on your Mac already uses that port (often a local MySQL on 3306). Pick another port in `.env`, then run `docker compose up --build` again:
```ini
DB_PORT=3307
APP_PORT=9090    # then open http://localhost:9090
```

**`required variable MYSQL_ROOT_PASSWORD is missing a value: set MYSQL_ROOT_PASSWORD in .env`**
You don't have a `.env` file yet, or a password in it is empty. Run `cp .env.example .env` and fill in the passwords.

**The app can't connect to the database after I changed the passwords in `.env`**
MySQL only reads the passwords when the database is first created. Either reset it with `docker compose down -v` (this deletes the data), or change the password inside MySQL to match.

**My changes to `schema.sql` / `seed.sql` don't show up**
The scripts only run on an empty database. Reset with `docker compose down -v` (this deletes the data), then `docker compose up --build`.

**My code changes don't show up**
Rebuild with `docker compose up --build`.

**The page shows an error or is blank**
Check the app's logs: `docker compose logs app`. Database connection problems are printed there.

**I want to free disk space**
Images and build cache can take a few GB. `docker builder prune` removes the build cache. `docker system df` shows what is using space.

---

## 12. Docker Desktop

On a Mac, Docker comes as the **Docker Desktop** app (https://www.docker.com/products/docker-desktop/). It must be **running** whenever you use `docker` commands; you'll see the whale icon in the menu bar. Its window also shows your containers, images and volumes, and has buttons to start, stop, delete and view logs, which you can use instead of the commands above.
