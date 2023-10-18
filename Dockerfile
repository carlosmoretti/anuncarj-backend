FROM maven:3.6.3 AS maven

WORKDIR /temp
COPY . /temp
RUN mvn package

# For Java 11, 
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /opt/app

# Copy the spring-boot-api-tutorial.jar from the maven stage to the /opt/app directory of the current stage.
COPY --from=maven /temp/target/*.jar /opt/app/anunciarj.jar

ENTRYPOINT ["java","-jar","anunciarj.jar"]