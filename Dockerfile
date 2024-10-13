FROM gradle:latest AS cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME=/home/gradle/cache_home
COPY build.gradle.* gradle.properties /home/gradle/app/
WORKDIR /home/gradle/app
RUN gradle clean build -i --stacktrace


FROM gradle:latest AS build
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY . /usr/src/app
WORKDIR /usr/src/app
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle buildFatJar --no-daemon

FROM amazoncorretto:22 AS runtime


RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/SeminarIndustrial-all.jar"]