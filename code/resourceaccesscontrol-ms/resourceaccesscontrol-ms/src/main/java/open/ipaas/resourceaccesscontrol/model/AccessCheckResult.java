package open.ipaas.resourceaccesscontrol.model;

import java.util.List;

public class AccessCheckResult {
    private boolean allowed;
    private String reason;
    private List<String> effectivePermissions;

    public AccessCheckResult() {}

    public AccessCheckResult(boolean allowed, String reason, List<String> effectivePermissions) {
        this.allowed = allowed;
        this.reason = reason;
        this.effectivePermissions = effectivePermissions;
    }

    // Getters and setters
    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<String> getEffectivePermissions() {
        return effectivePermissions;
    }

    public void setEffectivePermissions(List<String> effectivePermissions) {
        this.effectivePermissions = effectivePermissions;
    }
}
