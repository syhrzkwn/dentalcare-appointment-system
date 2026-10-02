# dentalcare-appointment-system
Dentalcare Appointment System is develop on top of Java Web (JSP, JSTL, and Servlet). It was a group assignment project for CSC584 - Enterprise Programming.

## Requirements
- JDK 8 or newer
- Maven 3.6+
- Apache Tomcat 9 (Tomcat 10+ uses the `jakarta.*` namespace and will not run this app)
- Apache Derby network server on `localhost:1527` (database `DentalcareDB`, user `app` / password `app`)

## Build and run
```sh
mvn package
cp target/dentalcare-appointment-system.war $CATALINA_HOME/webapps/
$CATALINA_HOME/bin/startup.sh
```
Then open http://localhost:8080/dentalcare-appointment-system/

JSTL and the Derby client driver are bundled in the WAR, so no extra jars need to be added to Tomcat.
