package open.ipaas.controller.model;

import java.util.List;

public class ResourceResponse {

    private String resourceId;
    private Object data;
    private List<String> permissions;

    // Default constructor
    public ResourceResponse() {}

    // Constructor with parameters
    public ResourceResponse(
        String resourceId,
        Object data,
        List<String> permissions
    ) {
        this.resourceId = resourceId;
        this.data = data;
        this.permissions = permissions;
    }

    // Getters and setters
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
