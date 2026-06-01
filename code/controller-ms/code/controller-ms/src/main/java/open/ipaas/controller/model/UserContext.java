package open.ipaas.controller.model;

import java.util.List;
import java.util.Map;

public class UserContext {

    private String userId;
    private List<String> groups;
    private Map<String, String> permissions;

    // Default constructor
    public UserContext() {}

    // Constructor with parameters
    public UserContext(
        String userId,
        List<String> groups,
        Map<String, String> permissions
    ) {
        this.userId = userId;
        this.groups = groups;
        this.permissions = permissions;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public Map<String, String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Map<String, String> permissions) {
        this.permissions = permissions;
    }
}
