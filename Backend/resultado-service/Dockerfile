FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} resultado-service.jar
ENTRYPOINT ["java","-jar","/resultado-service.jar"]