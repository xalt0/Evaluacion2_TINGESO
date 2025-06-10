FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} ms-3.jar
ENTRYPOINT ["java","-jar","/ms-3.jar"]