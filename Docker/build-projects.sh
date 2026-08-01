#!/bin/bash

cd ..

#Build service
cd userservice && ./mvnw clean package -DskipTests
