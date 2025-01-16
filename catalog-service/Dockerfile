# Stage 1: Build Stage with Maven
FROM maven:3.9.5-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy Maven configuration and dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copy the application source code
COPY src ./src

# Build the application
RUN mkdir -p /uploads && chmod -R 777 /uploads
RUN mvn clean package -DskipTests

# Stage 2: Runtime Stage with JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=builder /app/target/*.jar app.jar

# Set environment variables
ENV SPRING_DATASOURCE_URL=${CATALOG_DB_URL}
ENV SPRING_DATASOURCE_USERNAME=${DB_USER}
ENV SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}
ENV SERVER_PORT=${CATALOG_SERVER_PORT}
ENV KAFKA_CONSUMER_BOOTSTRAP_SERVERS=${KAFKA_CONSUMER_BOOTSTRAP_SERVERS}
ENV KAFKA_CONSUMER_GROUP_ID=${KAFKA_CONSUMER_GROUP_ID}
ENV KAFKA_PRODUCER_BOOTSTRAP_SERVERS=${KAFKA_PRODUCER_BOOTSTRAP_SERVERS}

# Expose the server port
EXPOSE ${SERVER_PORT}

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]