FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} llegada-service.jar
ENTRYPOINT ["java","-jar","/llegada-service.jar"]