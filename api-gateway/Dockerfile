FROM eclipse-temurin:24-jdk AS builder

WORKDIR /app

# Copy Maven build files first for better caching
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Package the application
RUN ./mvnw clean package -DskipTests

# Run the app with a slim JRE
FROM eclipse-temurin:24-jre AS runtime

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

# Expose your app's port
EXPOSE 4004

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]