# Stage 1: Build Stage with Maven
FROM maven:3.9.5-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy Maven configuration and dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copy the application source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime Stage with JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=builder /app/target/*.jar app.jar

# Set environment variables
ENV SERVER_PORT=${GATEWAY_SERVER_PORT}

# Expose the server port
EXPOSE ${SERVER_PORT}

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]