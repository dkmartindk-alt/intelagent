package open.ipaas.resourceaccesscontrol.model;

public class InheritanceRule {
    private String parentId;
    private String childId;
    private boolean inherits;

    public InheritanceRule() {}

    public InheritanceRule(String parentId, String childId, boolean inherits) {
        this.parentId = parentId;
        this.childId = childId;
        this.inherits = inherits;
    }

    // Getters and setters
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

    public boolean isInherits() {
        return inherits;
    }

    public void setInherits(boolean inherits) {
        this.inherits = inherits;
    }
}
