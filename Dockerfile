FROM openjdk:17-alpine

ARG JAR_FILE=/build/libs/expert-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /expert.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/expert.jar"]
