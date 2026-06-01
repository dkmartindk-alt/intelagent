package open.ipaas.resourceaccesscontrol.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import open.ipaas.resourceaccesscontrol.model.AccessCheckResult;
import open.ipaas.resourceaccesscontrol.model.InheritanceRule;
import open.ipaas.resourceaccesscontrol.model.Permission;
import open.ipaas.resourceaccesscontrol.service.ResourceAccessControlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secure-access")
@Tag(name = "Resource Access Control", description = "APIs for managing secure access to resources")
public class ResourceAccessControlController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceAccessControlController.class);

    @Autowired
    private ResourceAccessControlService resourceAccessControlService;

    @PostMapping("/resource")
    @Operation(summary = "Secure access to specific resource", description = "Check if user has secure access to a specific resource")
    public ResponseEntity<AccessCheckResult> secureAccessToResource(@RequestBody SecureAccessRequest request) {
        logger.info("Received request to check secure access for user {} to resource {}",
                   request.getUserId(), request.getResourceId());

        AccessCheckResult result = resourceAccessControlService.checkSecureAccessForResource(
            request.getUserId(),
            request.getResourceId(),
            request.getAction()
        );

        logger.info("Access check result for user {}: allowed={}", request.getUserId(), result.isAllowed());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/resource-contents")
    @Operation(summary = "Secure access to contents of a resource", description = "Check if user has secure access to contents of a resource")
    public ResponseEntity<AccessCheckResult> secureAccessToResourceContents(@RequestBody SecureAccessRequest request) {
        logger.info("Received request to check secure access for user {} to contents of resource {}",
                   request.getUserId(), request.getResourceId());

        AccessCheckResult result = resourceAccessControlService.checkSecureAccessForResourceContents(
            request.getUserId(),
            request.getResourceId(),
            request.getAction()
        );

        logger.info("Resource contents access check result for user {}: allowed={}", request.getUserId(), result.isAllowed());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/batch")
    @Operation(summary = "Secure access to multiple resources", description = "Check secure access to multiple resources in batch")
    public ResponseEntity<List<AccessCheckResult>> secureAccessToBatch(@RequestBody BatchAccessRequest request) {
        logger.info("Received batch access request for {} users and {} resources",
                   request.getUserIds().size(), request.getResourceIds().size());

        List<AccessCheckResult> results = resourceAccessControlService.checkSecureAccessForBatch(
            request.getUserIds(),
            request.getResourceIds(),
            request.getAction()
        );

        logger.info("Batch access check completed for {} requests", results.size());
        return ResponseEntity.ok(results);
    }

    @GetMapping("/user-permissions/{userId}/{resourceId}")
    @Operation(summary = "Get effective permissions", description = "Get effective permissions for a user on a specific resource")
    public ResponseEntity<List<Permission>> getUserEffectivePermissions(
            @PathVariable String userId,
            @PathVariable String resourceId) {
        logger.info("Received request to get effective permissions for user {} on resource {}", userId, resourceId);

        List<Permission> permissions = resourceAccessControlService.getUserEffectivePermissions(userId, resourceId);

        logger.debug("Found {} permissions for user {} on resource {}", permissions.size(), userId, resourceId);
        return ResponseEntity.ok(permissions);
    }

    @PostMapping("/permissions/check")
    @Operation(summary = "Check user permission", description = "Check if user has permission for resource")
    public ResponseEntity<AccessCheckResult> checkUserPermission(@RequestBody PermissionCheckRequest request) {
        logger.info("Received permission check request for user {} on resource {} with permission level {}",
                   request.getUserId(), request.getResourceId(), request.getPermissionLevel());

        AccessCheckResult result = resourceAccessControlService.checkUserPermission(
            request.getUserId(),
            request.getResourceId(),
            request.getPermissionLevel()
        );

        logger.info("Permission check result for user {}: allowed={}", request.getUserId(), result.isAllowed());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/inheritance/evaluate")
    @Operation(summary = "Evaluate inheritance rules", description = "Evaluate inheritance rules for user/resource")
    public ResponseEntity<List<InheritanceRule>> evaluateInheritance(@RequestBody InheritanceEvaluationRequest request) {
        logger.info("Received inheritance evaluation request for user {} and resource {}",
                   request.getUserId(), request.getResourceId());

        List<InheritanceRule> inheritanceRules = resourceAccessControlService.evaluateInheritanceRules(
            request.getUserId(),
            request.getResourceId()
        );

        logger.debug("Found {} inheritance rules for user {} and resource {}",
                    inheritanceRules.size(), request.getUserId(), request.getResourceId());
        return ResponseEntity.ok(inheritanceRules);
    }
}

// Data transfer objects
class SecureAccessRequest {
    private String userId;
    private String resourceId;
    private String action;

    // Constructors
    public SecureAccessRequest() {}

    public SecureAccessRequest(String userId, String resourceId, String action) {
        this.userId = userId;
        this.resourceId = resourceId;
        this.action = action;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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
}

class BatchAccessRequest {
    private List<String> userIds;
    private List<String> resourceIds;
    private String action;

    // Constructors
    public BatchAccessRequest() {}

    public BatchAccessRequest(List<String> userIds, List<String> resourceIds, String action) {
        this.userIds = userIds;
        this.resourceIds = resourceIds;
        this.action = action;
    }

    // Getters and setters
    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public List<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

class PermissionCheckRequest {
    private String userId;
    private String resourceId;
    private String permissionLevel;

    // Constructors
    public PermissionCheckRequest() {}

    public PermissionCheckRequest(String userId, String resourceId, String permissionLevel) {
        this.userId = userId;
        this.resourceId = resourceId;
        this.permissionLevel = permissionLevel;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel) {
        this.permissionLevel = permissionLevel;
    }
}

class InheritanceEvaluationRequest {
    private String userId;
    private String resourceId;

    // Constructors
    public InheritanceEvaluationRequest() {}

    public InheritanceEvaluationRequest(String userId, String resourceId) {
        this.userId = userId;
        this.resourceId = resourceId;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
