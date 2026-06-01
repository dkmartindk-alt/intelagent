# Endpoints

## Permission Checks
- `POST /secure-access/resource` - Secure access to specific resource
- `POST /secure-access/resource-contents` - Secure access to contents of a resource
- `POST /secure-access/batch` - Secure access to multiple resources
- `GET /secure-access/user-permissions/{userId}/{resourceId}` - Get effective permissions
- `POST /permissions/check` - Check if user has permission for resource
- `POST /inheritance/evaluate` - Evaluate inheritance rules for user/resource