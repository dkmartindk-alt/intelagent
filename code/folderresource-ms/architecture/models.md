# Models

## Folder
- id: String
- name: String
- parentId: String (nullable)
- createdAt: DateTime
- updatedAt: DateTime
- inheritsSecurity: Boolean
- ownerGroupId: String
- properties: Map<String, Object>

## FolderContent
- folderId: String
- contents: List<Resource>
- totalSize: Integer