#!/bin/bash
set -e

echo "🚀 Starting deployment..."

# Добавляем шаг сборки проекта с помощью Maven
echo "📦 Building the project with Maven..."
mvn clean package

echo "🐳 Navigating to the docker-compose file..."
# Скрипт уже находится в папке diplom, поэтому путь относительный
cd ./json

echo "🚀 Rebuilding and deploying containers..."
docker-compose up --build -d --remove-orphans

echo "✅ Deployment finished successfully!"