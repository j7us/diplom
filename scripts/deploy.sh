#!/bin/bash
set -e

echo "🚀 Starting deployment..."

# Добавляем шаг сборки проекта с помощью Maven
echo "📦 Building the project with Maven..."

echo "Начало в:"
date

mvn clean package

echo "Конец в:"
date

echo "🐳 Navigating to the docker-compose file..."
# Скрипт уже находится в папке diplom, поэтому путь относительный
cd ./json

echo "🚀 Rebuilding and deploying containers..."

echo "Начало в:"
date

docker-compose up --build -d --remove-orphans

echo "Конец в:"
date

echo "✅ Deployment finished successfully!"

22:43:23 -

22:44:50 - 22:45:00