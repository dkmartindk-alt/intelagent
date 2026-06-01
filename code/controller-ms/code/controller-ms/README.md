# Controller Microservice

This microservice handles resource management with access control and user context coordination.

## Features

- Resource operations (GET, POST, PUT, DELETE)
- User context management
- Access control validation
- OpenAPI/Swagger documentation
- Structured logging
- Virtual threads enabled

## Endpoints

### Resource Management
- `GET /resources/{id}/contents` - Get folder contents securely
- `GET /resources/{id}` - Get specific resource securely
- `POST /resources` - Create resource through secure workflow
- `PUT /resources/{id}` - Update resource through secure workflow
- `DELETE /resources/{id}` - Delete resource through secure workflow

### User Context
- `POST /context/user/{userId}` - Get user context with groups

## Getting Started

1. Clone the repository
2. Build the project: `./mvnw clean install`
3. Run the application: `./mvnw spring-boot:run`
4. Access the API at `http://localhost:8080`
5. View Swagger documentation at `http://localhost:8080/swagger-ui.html`

## Configuration

The application uses the following configuration:
- Port: 8080 (configurable via `server.port`)
- Virtual threads: Enabled via `spring.threads.virtual.enabled=true`
- Actuator endpoints: Health and info exposed

## Dependencies

- Spring Boot Web MVC
- Spring Boot Actuator
- SpringDoc OpenAPI UI
- SLF4J for logging