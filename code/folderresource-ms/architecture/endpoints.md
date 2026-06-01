# Endpoints

## Folder Operations
- `POST /folders` - Create folder resource
- `GET /folders/{id}` - Get folder details
- `PUT /folders/{id}` - Update folder
- `DELETE /folders/{id}` - Delete folder
- `GET /folders` - List folders

## Content Operations
- `GET /folders/{id}/contents` - Get contents of a folder
- `POST /folders/{id}/add-resource` - Add a resource to a folder
- `POST /folders/{id}/remove-resource` - Remove a resource from a folder
- `GET /folders/{id}/hierarchy` - Get folder hierarchy structure