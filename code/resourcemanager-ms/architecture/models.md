# Models

## Resource
- id: String
- name: String
- type: String
- parentId: String (nullable)
- metadata: Map<String, Object>
- createdAt: DateTime
- updatedAt: DateTime
- inheritsSecurity: Boolean
- ownerGroupId: String

## ResourceType
- id: String
- name: String
- serviceEndpoint: String
- description: String

## ResourceRelationship
- parentId: String
- childId: String
- relationshipType: String (CONTAINS, REFERENCES, DEPENDS_ON)