# GUI Microservice

This microservice provides a graphical user interface for managing resources and navigating folders. It runs on port 9001, integrates with Eureka for service discovery, and implements the following functionality:

## Features
- Resource management (folders and files)
- Navigation capabilities
- RESTful API endpoints
- OpenAPI/Swagger documentation
- Virtual thread support
- Eureka service registration and discovery

## Endpoints

### Resource Operations
- `GET /resources/{id}` - Get resource details
- `GET /resources/{id}/contents` - Get contents of a folder-type resource
- `POST /resources` - Create resource (folder)
- `DELETE /resources/{id}` - Delete resource (folder)

### Navigation
- `GET /` - Main dashboard
- `GET /browse/{folderId}` - Browse folder contents

## Configuration
- Port: 9001
- Virtual threads: Enabled
- Package: open.ipaas.gui

## Running the Service
To run the service locally:
```bash
mvn spring-boot:run
```

The service will be available at http://localhost:9001

Swagger UI will be available at http://localhost:9001/swagger-ui.html