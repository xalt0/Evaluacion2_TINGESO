FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} ms-1.jar
ENTRYPOINT ["java","-jar","/ms-1.jar"]