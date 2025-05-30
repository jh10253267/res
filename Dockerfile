FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /workspace/app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:17-jre-alpine

VOLUME /tmp

COPY --from=build /workspace/app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]