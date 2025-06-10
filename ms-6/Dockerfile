FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} ms-6.jar
ENTRYPOINT ["java","-jar","/ms-6.jar"]