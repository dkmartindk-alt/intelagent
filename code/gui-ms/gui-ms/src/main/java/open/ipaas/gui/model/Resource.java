package open.ipaas.gui.model;

import java.time.LocalDateTime;

public class Resource {
    private String id;
    private String name;
    private String type;
    private String parentId; // nullable
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean inheritsSecurity;
    private String ownerGroupId;

    public Resource() {}

    public Resource(String id, String name, String type, String parentId, LocalDateTime createdAt,
                    LocalDateTime updatedAt, Boolean inheritsSecurity, String ownerGroupId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.parentId = parentId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.inheritsSecurity = inheritsSecurity;
        this.ownerGroupId = ownerGroupId;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getParentId() {
        return parentId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Boolean getInheritsSecurity() {
        return inheritsSecurity;
    }

    public String getOwnerGroupId() {
        return ownerGroupId;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setInheritsSecurity(Boolean inheritsSecurity) {
        this.inheritsSecurity = inheritsSecurity;
    }

    public void setOwnerGroupId(String ownerGroupId) {
        this.ownerGroupId = ownerGroupId;
    }
}
