# Models

## User
- id: String
- username: String
- email: String
- role: String

## Resource
- id: String
- name: String
- type: String
- parentId: String (nullable)
- createdAt: DateTime
- updatedAt: DateTime
- inheritsSecurity: Boolean
- ownerGroupId: String