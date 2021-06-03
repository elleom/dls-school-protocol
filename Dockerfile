FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/*.jar
ENV DATABASE_URL="jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11412144"
ENV MYSQL_PASSWORD="VWIzCrKEjh"
ENV DATABASE_USER="sql11412144"
ENV DATABASE_URL_REPLICA="jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11416892"
ENV MYSQL_PASSWORD_REPLICA="z2IBZBMskH"
ENV DATABASE_USER_REPLICA="sql11416892"
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080/tcp
