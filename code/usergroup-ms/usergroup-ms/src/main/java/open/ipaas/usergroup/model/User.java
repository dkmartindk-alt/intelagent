package open.ipaas.usergroup.model;

import java.util.List;

public class User {
    private String id;
    private String username;
    private String email;
    private String roleId;
    private String groupId;

    public User() {
    }

    public User(String id, String username, String email, String roleId, String groupId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roleId = roleId;
        this.groupId = groupId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
