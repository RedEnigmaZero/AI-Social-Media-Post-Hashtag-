#!/bin/bash

# Compile and start the first microservice
cd ModerationService
mvn clean install
mvn spring-boot:run &

cd ..

# Compile and start the second microservice
cd HashtaggingService
mvn clean install 
mvn spring-boot:run &
cd ..

# Compile and launch the main app
cd MainApp
mvn clean install
java -jar target/hw2-0.0.1-SNAPSHOT.jar
