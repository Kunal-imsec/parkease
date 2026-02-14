FROM maven:3.9-eclipse-temurin-17-alpine AS build

WORKDIR /app

# Copy pom.xml and source
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Production stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/parkease-backend-1.0.0.jar app.jar

# Expose port
EXPOSE 8080

# Set JVM options for production
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Run the application
# Render provides PORT env variable, Spring profile from SPRING_PROFILES_ACTIVE
# CRITICAL: server.address=0.0.0.0 allows Render to detect the port
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8080} -Dserver.address=0.0.0.0 -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-prod} -jar app.jar"]
