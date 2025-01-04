FROM openjdk:25-jdk-slim

WORKDIR /app

COPY build/libs/*SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/spring-boot/app.jar"]