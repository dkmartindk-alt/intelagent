package open.ipaas.resourceaccesscontrol.model;

public class Permission {
    private String id;
    private String resourceId;
    private String userId;
    private String groupId;
    private String permissionLevel; // READ, WRITE, DELETE, ADMIN
    private String inheritedFrom;

    public Permission() {}

    public Permission(String id, String resourceId, String userId, String groupId, String permissionLevel, String inheritedFrom) {
        this.id = id;
        this.resourceId = resourceId;
        this.userId = userId;
        this.groupId = groupId;
        this.permissionLevel = permissionLevel;
        this.inheritedFrom = inheritedFrom;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public String getInheritedFrom() {
        return inheritedFrom;
    }

    public void setInheritedFrom(String inheritedFrom) {
        this.inheritedFrom = inheritedFrom;
    }
}
