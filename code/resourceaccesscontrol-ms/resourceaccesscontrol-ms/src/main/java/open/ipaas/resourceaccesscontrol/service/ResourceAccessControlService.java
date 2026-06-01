package open.ipaas.resourceaccesscontrol.service;

import open.ipaas.resourceaccesscontrol.model.AccessCheckResult;
import open.ipaas.resourceaccesscontrol.model.InheritanceRule;
import open.ipaas.resourceaccesscontrol.model.Permission;
import java.util.List;

public interface ResourceAccessControlService {

    /**
     * Check if a user has secure access to a specific resource
     */
    AccessCheckResult checkSecureAccessForResource(String userId, String resourceId, String action);

    /**
     * Check if a user has secure access to contents of a resource
     */
    AccessCheckResult checkSecureAccessForResourceContents(String userId, String resourceId, String action);

    /**
     * Check secure access for multiple resources in batch
     */
    List<AccessCheckResult> checkSecureAccessForBatch(List<String> userIds, List<String> resourceIds, String action);

    /**
     * Get effective permissions for a user on a specific resource
     */
    List<Permission> getUserEffectivePermissions(String userId, String resourceId);

    /**
     * Check if user has specific permission for a resource
     */
    AccessCheckResult checkUserPermission(String userId, String resourceId, String permissionLevel);

    /**
     * Evaluate inheritance rules for user/resource
     */
    List<InheritanceRule> evaluateInheritanceRules(String userId, String resourceId);
}
