# Endpoints

## Service Registration
- `POST /eureka/apps/{appName}` - Register a new service instance
- `DELETE /eureka/apps/{appName}/{instanceId}` - De-register a service instance
- `PUT /eureka/apps/{appName}/{instanceId}` - Update service instance status

## Service Discovery
- `GET /eureka/apps` - Get all registered applications
- `GET /eureka/apps/{appName}` - Get all instances of a specific application
- `GET /eureka/apps/{appName}/{instanceId}` - Get a specific instance
- `GET /eureka/vips/{vipAddress}` - Get applications by VIP address
- `GET /eureka/svips/{svipAddress}` - Get applications by secure VIP address

## Health Check
- `GET /eureka/apps/{appName}/{instanceId}/status` - Get instance status
- `GET /eureka/apps/{appName}/{instanceId}/health` - Health check endpoint

## Implementation Details
All endpoints are implemented in the `EurekaController` class using Spring Boot annotations. The controller uses the `EurekaService` to handle the business logic. All endpoints include structured logging using SLF4J.