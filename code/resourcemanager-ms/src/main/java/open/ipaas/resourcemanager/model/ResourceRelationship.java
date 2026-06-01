package open.ipaas.resourcemanager.model;

public class ResourceRelationship {
    private String parentId;
    private String childId;
    private String relationshipType; // CONTAINS, REFERENCES, DEPENDS_ON

    // Constructors
    public ResourceRelationship() {}

    public ResourceRelationship(String parentId, String childId, String relationshipType) {
        this.parentId = parentId;
        this.childId = childId;
        this.relationshipType = relationshipType;
    }

    // Getters and Setters
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }
}