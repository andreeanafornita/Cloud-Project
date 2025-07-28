#!/bin/bash


error_exit() {
    echo "$1" 1>&2
    exit 1
}


echo "Waiting for MySQL to initialize..."
until mysqladmin ping -h mysql -uroot -proot --silent; do
  echo "Waiting for MySQL..."
  sleep 2
done
echo "MySQL is ready!"


mysql -h mysql -uroot -proot -e "CREATE DATABASE IF NOT EXISTS imagesdb;" || error_exit "Failed to create MySQL database."


echo "Starting Node.js server..."
node /app/index.js || error_exit "Failed to start Node.js server."