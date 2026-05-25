FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/userservice-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "app.jar"]