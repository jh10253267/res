# 1. 빌드 단계
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /workspace/app

# Gradle wrapper와 프로젝트 파일 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# 빌드
RUN ./gradlew bootJar --no-daemon


# 2. 런타임 단계
FROM eclipse-temurin:17-jre-alpine

# Alpine에 tzdata 설치 후 KST 적용
RUN apk add --no-cache tzdata \
    && cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime \
    && echo "Asia/Seoul" > /etc/timezone \
    && apk del tzdata

VOLUME /tmp

# 빌드한 jar 복사
COPY --from=build /workspace/app/build/libs/*.jar app.jar

# JVM timezone 강제 지정 + 프로파일 지정
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]
