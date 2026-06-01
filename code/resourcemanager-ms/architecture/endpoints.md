# Endpoints

## Resource Operations
- `POST /resources` - Create resource (delegates to appropriate ResourceType MS)
- `GET /resources/{id}` - Get resource by ID (delegates to appropriate ResourceType MS)
- `PUT /resources/{id}` - Update resource (delegates to appropriate ResourceType MS)
- `DELETE /resources/{id}` - Delete resource (delegates to appropriate ResourceType MS)
- `GET /resources` - List resources with optional filters
- `GET /resources/{parentId}/children` - Get child resources of a parent

## Resource Type Registry
- `GET /resources/types` - List available resource types
- `POST /resources/types` - Register new resource type
- `GET /resources/types/{typeId}` - Get resource type details