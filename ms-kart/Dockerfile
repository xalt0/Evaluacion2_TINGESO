FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} ms-kart.jar
ENTRYPOINT ["java","-jar","/ms-kart.jar"]