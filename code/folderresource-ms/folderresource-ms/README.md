# FolderResource Microservice

This microservice handles folder management operations including creation, retrieval, updating, and deletion of folders, as well as managing their contents and hierarchy.

## Features

- RESTful API for folder management
- Folder hierarchy management
- Content management within folders
- Virtual thread support for improved performance
- Comprehensive logging
- OpenAPI/Swagger documentation
- Eureka service discovery integration

## Endpoints

### Folder Operations
- `POST /folders` - Create folder resource
- `GET /folders/{id}` - Get folder details
- `PUT /folders/{id}` - Update folder
- `DELETE /folders/{id}` - Delete folder
- `GET /folders` - List folders

### Content Operations
- `GET /folders/{id}/contents` - Get contents of a folder
- `POST /folders/{id}/add-resource` - Add a resource to a folder
- `POST /folders/{id}/remove-resource` - Remove a resource from a folder
- `GET /folders/{id}/hierarchy` - Get folder hierarchy structure

## Configuration

The service runs on port 9002 by default and uses virtual threads for improved concurrency. It registers with Eureka service discovery at http://localhost:8761/eureka.

## Dependencies

- Spring Boot 4.0.5
- Java 25
- Spring Web MVC
- Spring Boot Actuator
- Spring Cloud Netflix Eureka Client
- Springdoc OpenAPI