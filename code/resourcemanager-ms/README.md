# Resource Manager Microservice

This is a Spring Boot microservice that manages resources in the iPaas platform. It provides REST APIs for creating, retrieving, updating, and deleting resources, as well as managing resource relationships and types.

## Features

- RESTful APIs for resource management
- Resource hierarchy management (parent-child relationships)
- Resource type registry
- Integration with Eureka for service discovery
- OpenAPI/Swagger documentation
- Structured logging
- Virtual threads enabled

## Tech Stack

- Java 25
- Spring Boot 4.0.5
- Spring Cloud Netflix Eureka
- SpringDoc OpenAPI
- Maven

## Endpoints

### Resource Operations
- `POST /resources` - Create resource
- `GET /resources/{id}` - Get resource by ID
- `PUT /resources/{id}` - Update resource
- `DELETE /resources/{id}` - Delete resource
- `GET /resources` - List resources with optional filters
- `GET /resources/{parentId}/children` - Get child resources of a parent

### Resource Type Registry
- `GET /resources/types` - List available resource types
- `POST /resources/types` - Register new resource type
- `GET /resources/types/{typeId}` - Get resource type details

## Configuration

The service registers with Eureka at `http://localhost:8761/eureka` by default. This can be changed in `application.properties`.

Virtual threads are enabled by default with the setting `spring.threads.virtual.enabled=true`.

## Running the Application

```bash
./mvnw spring-boot:run
```

The application will start on port 8082 by default.

## API Documentation

API documentation is available at `/swagger-ui.html` when the application is running.