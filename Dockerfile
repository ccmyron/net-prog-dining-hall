FROM openjdk:8-jdk-alpine
MAINTAINER chris
COPY target/dining_hall-0.0.1.jar dining_hall-0.0.1.jar
ENTRYPOINT ["java","-jar","/dining_hall-0.0.1.jar"]
EXPOSE 9090