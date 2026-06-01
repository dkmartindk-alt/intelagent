package open.ipaas.controller.model;

public class ResourceRequest {

    private String resourceId;
    private String action; // READ, WRITE, DELETE, EXECUTE
    private UserContext userContext;

    // Default constructor
    public ResourceRequest() {}

    // Constructor with parameters
    public ResourceRequest(
        String resourceId,
        String action,
        UserContext userContext
    ) {
        this.resourceId = resourceId;
        this.action = action;
        this.userContext = userContext;
    }

    // Getters and setters
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public UserContext getUserContext() {
        return userContext;
    }

    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }
}
