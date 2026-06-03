#!/bin/bash

set -e

cd ..

services=(
  configurationservice
  eurekaservice
  businessservice
)

for service in "${services[@]}"
do
  echo "Building $service..."
  cd "$service"
  ./mvnw clean package -DskipTests
  cd ..
done

echo "All services built successfully."
