FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/*.jar app.jar
COPY src/main/resources /app/resources

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]