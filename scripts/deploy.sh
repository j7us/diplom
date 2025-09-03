#!/bin/bash

# Строка ниже заставит скрипт остановиться, если любая команда завершится с ошибкой.
# Это хорошая практика для надежности.
set -e

echo "🚀 Starting deployment..."

# Переходим в директорию с проектом.
# Путь /home/docker-user/diplom — это то, куда скрипт клонирует проект внутри DinD-контейнера.
cd /home/docker-user/diplom

echo "🔄 Pulling latest changes from repository..."
git pull origin master

echo "🐳 Navigating to the docker-compose file..."
cd json

echo "🚀 Rebuilding and deploying containers..."
# Та же самая команда, что и была
docker-compose up --build -d --remove-orphans

echo "✅ Deployment finished successfully!"