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
