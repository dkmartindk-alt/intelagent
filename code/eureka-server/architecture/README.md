# Eureka Server Architecture

## Overview
The Eureka Server is a service registry that enables service discovery in a microservices architecture. It allows services to register themselves and discover other services.

## Components

### 1. REST Controller Layer (`EurekaController`)
Handles all incoming HTTP requests and routes them to the appropriate service methods.
- Registration API: `/eureka/apps/{appName}`
- Discovery API: `/eureka/apps`, `/eureka/apps/{appName}`, etc.
- Health Check API: `/eureka/apps/{appName}/{instanceId}/health`

### 2. Service Layer (`EurekaService` and `EurekaServiceImpl`)
Contains the business logic for managing service registrations and discoveries.
- Maintains the registry of services
- Handles CRUD operations for service instances
- Provides health check functionality

### 3. Data Models
- `ServiceInstance`: Represents a registered service instance
- `Application`: Groups service instances by application name
- `RegistrationRequest`: Encapsulates service registration data
- `DiscoveryResponse`: Response object for discovery operations

### 4. In-Memory Registry
Uses concurrent hash maps to store service instances organized by application name.
- Thread-safe for concurrent access
- Stores service metadata, status, and network information

## Endpoints

### Service Registration
- `POST /eureka/apps/{appName}` - Register a new service instance
- `DELETE /eureka/apps/{appName}/{instanceId}` - De-register a service instance
- `PUT /eureka/apps/{appName}/{instanceId}?status={status}` - Update service instance status

### Service Discovery
- `GET /eureka/apps` - Get all registered applications
- `GET /eureka/apps/{appName}` - Get all instances of a specific application
- `GET /eureka/apps/{appName}/{instanceId}` - Get a specific instance
- `GET /eureka/vips/{vipAddress}` - Get applications by VIP address
- `GET /eureka/svips/{svipAddress}` - Get applications by secure VIP address

### Health Check
- `GET /eureka/apps/{appName}/{instanceId}/status` - Get instance status
- `GET /eureka/apps/{appName}/{instanceId}/health` - Health check endpoint

## Configuration
- Port: 8761 (default)
- Self-registration disabled (`eureka.client.register-with-eureka=false`)
- Fetch registry disabled (`eureka.client.fetch-registry=false`)
- Virtual threads enabled (`spring.threads.virtual.enabled=true`)

## Dependencies
- Spring Boot Web MVC
- Spring Cloud Netflix Eureka Server
- Spring Boot Actuator
- SpringDoc OpenAPI UI