# Étape 1: Build
FROM openjdk:21-oracle AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Étape 2: Runtime
FROM gcr.io/distroless/java21
WORKDIR /app
COPY --from=build /app/target/account-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]