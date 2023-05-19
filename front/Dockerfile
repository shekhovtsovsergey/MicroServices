FROM maven:3.8.3-openjdk-17-slim AS build
COPY . /app
WORKDIR /app
RUN mvn clean install
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/app/target/app.jar"]