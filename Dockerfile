# Multi-stage build: First stage builds the JAR
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Install Maven (since .mvn directory might not be in repo)
RUN apk add --no-cache maven

# Copy pom.xml first for dependency caching
COPY pom.xml ./

# Download dependencies (cached layer if pom.xml doesn't change)
RUN mvn dependency:go-offline -B || true

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Second stage: Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/pos-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render will set PORT env var)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]

