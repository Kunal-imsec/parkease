#!/bin/bash
# Render start script for ParkEase Backend

echo "Starting ParkEase Backend..."

# Run the Spring Boot application with production profile
java -Dserver.port=$PORT -Dspring.profiles.active=prod -jar target/parkease-backend-1.0.0.jar
