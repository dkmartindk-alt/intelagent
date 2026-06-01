# Microservices Summary

## 1. Eureka Server
**Purpose**: Service registry and discovery for the microservices architecture
**Key Functions**:
- Register and maintain service instances
- Enable service-to-service communication through discovery
- Monitor service health and availability
- Provide load balancing information

**Endpoints**:
- `POST /eureka/apps/{appName}` - Register a new service instance
- `DELETE /eureka/apps/{appName}/{instanceId}` - De-register a service instance
- `GET /eureka/apps` - Get all registered applications
- `GET /eureka/apps/{appName}` - Get all instances of a specific application

## 2. GUI MS
**Purpose**: Administrative user interface for the IPaaS solution
**Key Functions**:
- Display folder hierarchies and resource lists
- Provide forms for resource management
- Handle user interactions and navigation
- Communicate with Controller MS via service discovery

**Endpoints (Initial Demo)**:
- `GET /browse/{folderId}` - Browse folder contents
- `POST /resources` - Create resource (folder)
- `DELETE /resources/{id}` - Delete resource (folder)

## 3. Controller MS
**Purpose**: Central orchestrator of the management plane
**Key Functions**:
- Receive requests from GUI MS
- Coordinate multi-step business workflows
- Act as state orchestrator
- Forward requests with user/group context to ResourceAccessControl MS using service discovery
- Aggregate responses from downstream services

**Endpoints (Initial Demo)**:
- `GET /resources/{id}/contents` - Get folder contents securely
- `POST /resources` - Create resource through secure workflow
- `DELETE /resources/{id}` - Delete resource through secure workflow

## 4. ResourceAccessControl MS
**Purpose**: Mandatory security gateway between Controller and Resource data layer
**Key Functions**:
- Evaluate permissions for parent containers
- Process inheritance logic in-memory
- Filter unauthorized items from responses
- Query specific ResourceType MS to fetch items using service discovery
- Implement security inheritance rules

**Endpoints (Initial Demo)**:
- `POST /secure-access/resource` - Secure access to specific resource
- `POST /secure-access/resource-contents` - Secure access to contents of a resource
- `POST /permissions/check` - Check if user has permission for resource

## 5. UserGroup MS
**Purpose**: User and group management service
**Key Functions**:
- Manage users, roles, and administrative group mappings
- Handle user and group CRUD operations
- Associate with ResourceAccessControl MS for permission checks using service discovery

**Endpoints (Initial Demo)**:
- `POST /users` - Create user
- `GET /users/{id}` - Get user by ID
- `POST /groups/{id}/add-user` - Add user to group

## 6. ResourceManager MS
**Purpose**: Resource routing and registry service
**Key Functions**:
- Maintain registry of different resource types and their corresponding services
- Route requests to appropriate specific `<ResourceType> MS` using service discovery
- Remain "security-blind" and rely on ResourceAccessControl MS for protection
- Handle resource relationships and hierarchies

**Endpoints (Initial Demo)**:
- `POST /resources` - Create resource (delegates to appropriate ResourceType MS)
- `GET /resources/{id}` - Get resource by ID (delegates to appropriate ResourceType MS)
- `GET /resources/types` - List available resource types

## 7. FolderResource MS
**Purpose**: Specialized service for folder-type resources
**Key Functions**:
- Handle folder-specific operations like listing contents
- Maintain parent-child relationships between folders and other resources
- Treat folders as a special type of resource for consistency

**Endpoints (Initial Demo)**:
- `POST /folders` - Create folder resource
- `GET /folders/{id}/contents` - Get contents of a folder
- `DELETE /folders/{id}` - Delete folder

## Communication Flow (With Service Discovery):
1. All services register with Eureka Server at startup
2. GUI MS discovers Controller MS via Eureka and sends user requests
3. Controller MS discovers ResourceAccessControl MS via Eureka
4. ResourceAccessControl MS discovers UserGroup MS and ResourceManager MS via Eureka
5. ResourceManager MS discovers FolderResource MS via Eureka
6. Services communicate through service discovery rather than direct URLs

## Security Features:
- Mandatory security gateway for all resource access
- Inheritance-based permission model
- In-memory filtering to prevent N+1 queries
- Separation of concerns between resource structure and access control
- Service discovery for dynamic service communication