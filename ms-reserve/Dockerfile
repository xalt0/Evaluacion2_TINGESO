FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} ms-reserve.jar
ENTRYPOINT ["java","-jar","/ms-reserve.jar"]