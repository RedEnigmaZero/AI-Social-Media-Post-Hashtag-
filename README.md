# AI Social Media Post Hashtag Generator

## Overview

This project is a social media post processing application built using a Java microservice architecture. It takes social media posts, moderates them, and then uses a local AI model via Ollama to generate relevant hashtags.

The application is structured into three main components:
* **`MainApp`**: The main application that reads posts (e.g., from `input.json`), orchestrates the workflow, and prints the final processed posts.
* **`ModerationService`**: A Spring Boot microservice responsible for content moderation.
* **`HashtaggingService`**: A Spring Boot microservice that connects to a local Ollama instance to generate AI-powered hashtags for posts.

## Prerequisites

To build and run this project, you will need:
* **Java 21** (or compatible)
* **Apache Maven**
* **Ollama**: This project requires a local Ollama instance to be running for the `HashtaggingService` to function. You can download it from [ollama.com](https://ollama.com/).
    * You must also have a model pulled, e.g., `ollama pull llama3`. The service is likely configured to use a specific model.

## How to Run

You must run the services in order, as the `MainApp` depends on them. You will need four separate terminal windows.

### 1. Start Ollama (Terminal 1)

Ensure your local Ollama server is running.
```bash
# This command typically runs the server in the background
ollama serve
```

### 2. Start the ModerationService (Terminal 2)

This service handles content safety checks.
```bash
# Navigate to the ModerationService directory
cd ModerationService

# Compile and install the package
mvn clean install

# Run the service
mvn spring-boot:run
```

### 3. Start the HashtaggingService (Terminal 3)

This service connects to Ollama to generate hashtags.
```bash
# Navigate to the HashtaggingService directory
cd HashtaggingService

# Compile and install the package
mvn clean install

# Run the service
mvn spring-boot:run
```

### 4. Run the MainApp (Terminal 4)

Once both microservices are running, you can run the main application to process the posts.
```bash
# Navigate to the MainApp directory
cd MainApp

# Compile and install the package
mvn clean install

# Run the main application (the JAR name might differ slightly)
java -jar target/hw2-0.0.1-SNAPSHOT.jar
```
The MainApp will process the input, send requests to the two services, and output the results to the console.
