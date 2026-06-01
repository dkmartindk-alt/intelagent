package open.ipaas.resourcemanager.dto;

import java.util.Map;

public class ResourceRequest {
    private String name;
    private String type;
    private String parentId;
    private Map<String, Object> metadata;
    private Boolean inheritsSecurity;
    private String ownerGroupId;

    // Constructors
    public ResourceRequest() {}

    public ResourceRequest(String name, String type, String parentId, Map<String, Object> metadata,
                           Boolean inheritsSecurity, String ownerGroupId) {
        this.name = name;
        this.type = type;
        this.parentId = parentId;
        this.metadata = metadata;
        this.inheritsSecurity = inheritsSecurity;
        this.ownerGroupId = ownerGroupId;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public Boolean getInheritsSecurity() {
        return inheritsSecurity;
    }

    public void setInheritsSecurity(Boolean inheritsSecurity) {
        this.inheritsSecurity = inheritsSecurity;
    }

    public String getOwnerGroupId() {
        return ownerGroupId;
    }

    public void setOwnerGroupId(String ownerGroupId) {
        this.ownerGroupId = ownerGroupId;
    }
}