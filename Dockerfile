# Building the APP from Maven image
FROM maven:3.6.0-jdk-11-slim AS build
RUN mvn install

FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]