FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} ms-4.jar
ENTRYPOINT ["java","-jar","/ms-4.jar"]