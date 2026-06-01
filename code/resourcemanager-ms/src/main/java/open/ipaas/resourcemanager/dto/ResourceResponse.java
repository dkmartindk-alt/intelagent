package open.ipaas.resourcemanager.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ResourceResponse {
    private String id;
    private String name;
    private String type;
    private String parentId;
    private Map<String, Object> metadata;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean inheritsSecurity;
    private String ownerGroupId;

    // Constructors
    public ResourceResponse() {}

    public ResourceResponse(String id, String name, String type, String parentId, Map<String, Object> metadata,
                            LocalDateTime createdAt, LocalDateTime updatedAt, Boolean inheritsSecurity, String ownerGroupId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.parentId = parentId;
        this.metadata = metadata;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.inheritsSecurity = inheritsSecurity;
        this.ownerGroupId = ownerGroupId;
    }

    // Static method to convert from Resource model
    public static ResourceResponse fromResource(open.ipaas.resourcemanager.model.Resource resource) {
        return new ResourceResponse(
            resource.getId(),
            resource.getName(),
            resource.getType(),
            resource.getParentId(),
            resource.getMetadata(),
            resource.getCreatedAt(),
            resource.getUpdatedAt(),
            resource.getInheritsSecurity(),
            resource.getOwnerGroupId()
        );
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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