# Base image
FROM eclipse-temurin:17-jdk
# Set working directory
WORKDIR /app
# Copy the source code to the container
COPY . .
# Make the mvnw script executable
RUN chmod +x ./mvnw
# Compiling with Maven
RUN ./mvnw package -DskipTests
# Expose the port
EXPOSE 8081
# Run the application
ENTRYPOINT ["java", "-jar", "target/energy-mix-backend.jar"]