#!/bin/bash
# Render build script for ParkEase Backend

echo "Starting Maven build..."

# Clean and package the application
./mvnw clean package -DskipTests

echo "Build completed successfully!"
