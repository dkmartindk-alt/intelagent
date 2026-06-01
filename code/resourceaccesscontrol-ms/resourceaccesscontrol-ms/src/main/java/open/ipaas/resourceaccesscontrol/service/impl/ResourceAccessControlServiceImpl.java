package open.ipaas.resourceaccesscontrol.service.impl;

import open.ipaas.resourceaccesscontrol.model.AccessCheckResult;
import open.ipaas.resourceaccesscontrol.model.InheritanceRule;
import open.ipaas.resourceaccesscontrol.model.Permission;
import open.ipaas.resourceaccesscontrol.service.ResourceAccessControlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceAccessControlServiceImpl implements ResourceAccessControlService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceAccessControlServiceImpl.class);

    @Override
    public AccessCheckResult checkSecureAccessForResource(String userId, String resourceId, String action) {
        logger.info("Checking secure access for user {} to resource {} with action {}", userId, resourceId, action);

        // This is a simplified implementation - in a real system, this would query a database or policy engine
        List<Permission> userPermissions = getUserEffectivePermissions(userId, resourceId);

        boolean hasPermission = userPermissions.stream()
                .anyMatch(permission ->
                    permission.getResourceId().equals(resourceId) &&
                    hasRequiredPermission(permission.getPermissionLevel(), action)
                );

        String reason = hasPermission ?
            "User has required permission for resource" :
            "User does not have required permission for resource";

        List<String> effectivePerms = userPermissions.stream()
            .map(Permission::getPermissionLevel)
            .collect(Collectors.toList());

        return new AccessCheckResult(hasPermission, reason, effectivePerms);
    }

    @Override
    public AccessCheckResult checkSecureAccessForResourceContents(String userId, String resourceId, String action) {
        logger.info("Checking secure access for user {} to contents of resource {} with action {}", userId, resourceId, action);

        // Check direct permissions on the resource
        AccessCheckResult directAccess = checkSecureAccessForResource(userId, resourceId, action);

        if (directAccess.isAllowed()) {
            return directAccess;
        }

        // Check if user has permissions through inheritance
        List<InheritanceRule> inheritanceRules = evaluateInheritanceRules(userId, resourceId);

        boolean inheritedAccess = inheritanceRules.stream()
                .filter(InheritanceRule::isInherits)
                .anyMatch(rule -> {
                    // Check if parent resource allows access
                    AccessCheckResult parentAccess = checkSecureAccessForResource(userId, rule.getParentId(), action);
                    return parentAccess.isAllowed();
                });

        String reason = inheritedAccess ?
            "User has inherited access to resource contents" :
            "User does not have access to resource contents";

        List<String> effectivePerms = inheritedAccess ?
            List.of("INHERITED_ACCESS") :
            new ArrayList<>();

        return new AccessCheckResult(inheritedAccess, reason, effectivePerms);
    }

    @Override
    public List<AccessCheckResult> checkSecureAccessForBatch(List<String> userIds, List<String> resourceIds, String action) {
        logger.info("Checking secure access for batch: {} users, {} resources", userIds.size(), resourceIds.size());

        List<AccessCheckResult> results = new ArrayList<>();

        for (String userId : userIds) {
            for (String resourceId : resourceIds) {
                AccessCheckResult result = checkSecureAccessForResource(userId, resourceId, action);
                results.add(result);
            }
        }

        return results;
    }

    @Override
    public List<Permission> getUserEffectivePermissions(String userId, String resourceId) {
        logger.debug("Getting effective permissions for user {} on resource {}", userId, resourceId);

        // This is a mock implementation - in a real system, this would query a database
        List<Permission> permissions = new ArrayList<>();

        // Mock permissions for demonstration purposes
        permissions.add(new Permission(
            "perm-" + userId + "-" + resourceId,
            resourceId,
            userId,
            null,
            "READ",
            null
        ));

        // Add admin permission for specific user
        if ("admin".equals(userId)) {
            permissions.add(new Permission(
                "admin-perm-" + userId + "-" + resourceId,
                resourceId,
                userId,
                null,
                "ADMIN",
                null
            ));
        }

        return permissions;
    }

    @Override
    public AccessCheckResult checkUserPermission(String userId, String resourceId, String permissionLevel) {
        logger.info("Checking if user {} has permission {} for resource {}", userId, permissionLevel, resourceId);

        List<Permission> userPermissions = getUserEffectivePermissions(userId, resourceId);

        boolean hasPermission = userPermissions.stream()
                .anyMatch(permission ->
                    permission.getResourceId().equals(resourceId) &&
                    permission.getPermissionLevel().equalsIgnoreCase(permissionLevel)
                );

        String reason = hasPermission ?
            "User has required permission level" :
            "User does not have required permission level";

        List<String> effectivePerms = userPermissions.stream()
            .map(Permission::getPermissionLevel)
            .collect(Collectors.toList());

        return new AccessCheckResult(hasPermission, reason, effectivePerms);
    }

    @Override
    public List<InheritanceRule> evaluateInheritanceRules(String userId, String resourceId) {
        logger.debug("Evaluating inheritance rules for user {} and resource {}", userId, resourceId);

        // This is a mock implementation - in a real system, this would query a database
        List<InheritanceRule> inheritanceRules = new ArrayList<>();

        // Mock inheritance rule for demonstration purposes
        inheritanceRules.add(new InheritanceRule(
            "parent-" + resourceId,
            resourceId,
            true
        ));

        return inheritanceRules;
    }

    /**
     * Helper method to determine if a permission level grants access for a specific action
     */
    private boolean hasRequiredPermission(String permissionLevel, String action) {
        if (permissionLevel == null || action == null) {
            return false;
        }

        switch (permissionLevel.toUpperCase()) {
            case "ADMIN":
                return true; // Admin has all permissions
            case "DELETE":
                // DELETE implies READ, WRITE permissions too
                return !action.equalsIgnoreCase("WRITE") &&
                       !action.equalsIgnoreCase("READ") &&
                       !action.equalsIgnoreCase("DELETE");
            case "WRITE":
                // WRITE implies READ permission too
                return !action.equalsIgnoreCase("READ") &&
                       !action.equalsIgnoreCase("WRITE");
            case "READ":
                return action.equalsIgnoreCase("READ");
            default:
                return false;
        }
    }
}
