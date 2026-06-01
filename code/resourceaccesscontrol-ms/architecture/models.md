# Models

## Permission
- id: String
- resourceId: String
- userId: String (nullable)
- groupId: String (nullable)
- permissionLevel: String (READ, WRITE, DELETE, ADMIN)
- inheritedFrom: String (nullable)

## AccessCheckResult
- allowed: Boolean
- reason: String
- effectivePermissions: List<String>

## InheritanceRule
- parentId: String
- childId: String
- inherits: Boolean