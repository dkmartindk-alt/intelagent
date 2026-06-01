# Models

## UserContext
- userId: String
- groups: List<String>
- permissions: Map<String, String>

## ResourceRequest
- resourceId: String
- action: String (READ, WRITE, DELETE, EXECUTE)
- userContext: UserContext

## ResourceResponse
- resourceId: String
- data: Object
- permissions: List<String>