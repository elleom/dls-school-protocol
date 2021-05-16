FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV DATABASE_URL="jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11412144"
ENV MYSQL_PASSWORD="VWIzCrKEjh"
ENV DATABASE_USER="sql11412144"
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080/tcp
