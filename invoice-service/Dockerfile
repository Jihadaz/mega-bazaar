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
ENV SPRING_DATASOURCE_URL="jdbc:mysql://invoice-service-db:3306/InvoiceService_db?allowPublicKeyRetrieval=true&useSSL=false&createDatabaseIfNotExist=true"
ENV SPRING_DATASOURCE_USERNAME="root"
ENV SPRING_DATASOURCE_PASSWORD="rootpassword"
ENV SERVER_PORT=8083
ENV KAFKA_CONSUMER_BOOTSTRAP_SERVERS="kafka-server:9093"
ENV KAFKA_CONSUMER_GROUP_ID="megabazaar-group"
ENV MAIL_TRAP_HOST="sandbox.smtp.mailtrap.io"
ENV MAIL_TRAP_PORT=2525
ENV MAIL_TRAP_USERNAME="xyz"
ENV MAIL_TRAP_PASSWORD="xyz"

# Expose the server port
EXPOSE ${SERVER_PORT}

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]