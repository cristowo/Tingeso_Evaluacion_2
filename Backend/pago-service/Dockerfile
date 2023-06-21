FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} pago-service.jar
ENTRYPOINT ["java","-jar","/pago-service.jar"]