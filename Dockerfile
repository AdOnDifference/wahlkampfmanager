# --- Build-Stage ---
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
# Dependencies cachen
RUN mvn -q -B -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -B -DskipTests package

# --- Run-Stage ---
FROM eclipse-temurin:21-jre
WORKDIR /app
# Render setzt $PORT -> an Spring durchreichen
ENV PORT=8080
EXPOSE 8080
# Nimmt einfach das erzeugte SNAPSHOT-JAR, egal wie es heißt
COPY --from=build /app/target/*SNAPSHOT.jar app.jar

# Optional: RAM begrenzen, wenn nötig
ENV JAVA_OPTS=""
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar --server.port=${PORT}"]
