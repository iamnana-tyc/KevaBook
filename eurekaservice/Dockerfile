FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/eurekaservice-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "app.jar"]