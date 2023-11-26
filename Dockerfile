FROM openjdk:17-jdk-alpine
COPY target/MSTxFleet-Auth-0.0.1-SNAPSHOT.jar MSTxFleet-Auth.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/MSTxFleet-Auth.jar"]