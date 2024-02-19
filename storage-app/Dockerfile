#FROM maven:3.8.3-openjdk-17-slim AS build
#ENV APP_RESOURCES_PATH=/app/resources
#COPY . /app
#WORKDIR /app
#RUN mvn package
#FROM openjdk:17.0.1-jdk-slim
#ENV APP_RESOURCES_PATH=/app/resources
#COPY --from=build /app/target/*.jar /app/app.jar
#EXPOSE 8191
#RUN mkdir -p /app/storage
#VOLUME ./storage:/app/storage
#RUN chmod -R a+rwx /app/storage
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]
FROM maven:3.8.3-openjdk-17-slim AS build
ENV APP_RESOURCES_PATH=/app/resources
COPY . /app
WORKDIR /app
RUN mvn package

FROM openjdk:17.0.1-jdk-slim
ENV APP_RESOURCES_PATH=/app/resources
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8191
RUN mkdir -p /app/storage
COPY ./ /app/storage
RUN chmod -R a+rwx /app/storage
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
