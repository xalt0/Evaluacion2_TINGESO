FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} ms-2.jar
ENTRYPOINT ["java","-jar","/ms-2.jar"]