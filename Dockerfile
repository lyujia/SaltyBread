FROM openjdk:21-jdk-slim AS builder
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar
FROM openjdk:21-slim

EXPOSE 8000

COPY --from=builder build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]