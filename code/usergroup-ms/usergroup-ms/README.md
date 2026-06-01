# UserGroup Microservice

A Spring Boot microservice for managing users and groups with Eureka discovery and OpenAPI documentation.

## Features

- RESTful API for user and group management
- Eureka client for service discovery
- OpenAPI/Swagger documentation
- Virtual threads enabled
- Structured logging with SLF4J

## Endpoints

### User Operations
- `POST /users` - Create user
- `GET /users/{id}` - Get user by ID
- `PUT /users/{id}` - Update user
- `DELETE /users/{id}` - Delete user
- `GET /users` - List users

### Group Operations
- `POST /groups` - Create group
- `GET /groups/{id}` - Get group by ID
- `PUT /groups/{id}` - Update group
- `DELETE /groups/{id}` - Delete group
- `GET /groups` - List groups
- `POST /groups/{id}/add-user?userId={userId}` - Add user to group
- `POST /groups/{id}/remove-user?userId={userId}` - Remove user from group

## Models

### User
- id: String
- username: String
- email: String
- roleId: String
- groupId: String

### Group
- id: String
- name: String
- description: String
- parentId: String (nullable)

### Role
- id: String
- name: String
- permissions: List<String>

## Setup

1. Ensure you have Java 25 and Maven installed
2. Make sure Eureka server is running on http://localhost:8761/eureka/
3. Build the project: `mvn clean install`
4. Run the application: `mvn spring-boot:run`

## API Documentation

API documentation is available at:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Configuration

The application is configured through `application.properties`:
- Server port: 8080
- Virtual threads: Enabled
- Eureka registration: Enabled