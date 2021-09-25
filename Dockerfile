FROM openjdk:8-jdk-alpine
MAINTAINER chris
COPY target/dining_hall-0.0.1-SNAPSHOT.jar dining_hall-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/dining_hall-0.0.1-SNAPSHOT.jar"]
EXPOSE 9090