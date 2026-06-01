# Models

## User
- id: String
- username: String
- email: String
- roleId: String
- groupId: String

## Group
- id: String
- name: String
- description: String
- parentId: String (nullable)

## Role
- id: String
- name: String
- permissions: List<String>