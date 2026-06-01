package open.ipaas.controller.service;

import open.ipaas.controller.model.ResourceRequest;
import open.ipaas.controller.model.ResourceResponse;
import open.ipaas.controller.model.UserContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {
    private static final Logger logger = LoggerFactory.getLogger(
        ResourceService.class
    );

    /**
     * Process a resource request based on user context and permissions
     */
    public ResourceResponse processResourceRequest(ResourceRequest request) {
        logger.info(
            "Processing resource request for resource: {} with action: {}",
            request.getResourceId(),
            request.getAction()
        );

        // Validate user permissions
        boolean hasPermission = validateUserPermissions(request);

        if (!hasPermission) {
            logger.warn(
                "User does not have permission for resource: {} and action: {}",
                request.getResourceId(),
                request.getAction()
            );
            throw new RuntimeException("Insufficient permissions");
        }

        // Simulate retrieving resource data
        Object resourceData = retrieveResourceData(request.getResourceId());

        // Create response with appropriate permissions
        List<String> permissions = getUserResourcePermissions(
            request.getUserContext(),
            request.getResourceId()
        );

        ResourceResponse response = new ResourceResponse();
        response.setResourceId(request.getResourceId());
        response.setData(resourceData);
        response.setPermissions(permissions);

        logger.info(
            "Successfully processed resource request for: {}",
            request.getResourceId()
        );
        return response;
    }

    /**
     * Get user context with groups
     */
    public UserContext getUserContext(String userId) {
        logger.info("Retrieving context for user: {}", userId);

        // In a real implementation, this would come from a database or authentication service
        List<String> groups = new ArrayList<>();
        groups.add("default-group");

        Map<String, String> permissions = new HashMap<>();
        permissions.put("resource-access", "read-write");

        UserContext userContext = new UserContext(userId, groups, permissions);

        logger.info("Retrieved context for user: {}", userId);
        return userContext;
    }

    /**
     * Validate user permissions for the requested action
     */
    private boolean validateUserPermissions(ResourceRequest request) {
        UserContext userContext = request.getUserContext();
        String action = request.getAction();
        String resourceId = request.getResourceId();

        // In a real implementation, this would check actual permissions
        // For now, we'll allow basic access based on user context
        if (userContext == null || userContext.getUserId() == null) {
            return false;
        }

        // Check if user has appropriate permissions for the action
        Map<String, String> permissions = userContext.getPermissions();
        if (permissions != null) {
            String resourcePermission = permissions.get("resource-access");
            if (
                "READ".equals(action) &&
                resourcePermission != null &&
                (resourcePermission.contains("read") ||
                    resourcePermission.contains("write"))
            ) {
                return true;
            } else if (
                ("WRITE".equals(action) || "DELETE".equals(action)) &&
                resourcePermission != null &&
                resourcePermission.contains("write")
            ) {
                return true;
            }
        }

        return true; // Allow for demo purposes
    }

    /**
     * Retrieve resource data by ID
     */
    private Object retrieveResourceData(String resourceId) {
        // In a real implementation, this would fetch from a database or external service
        Map<String, Object> mockData = new HashMap<>();
        mockData.put("id", resourceId);
        mockData.put("name", "Resource " + resourceId);
        mockData.put("type", "folder");
        mockData.put("createdDate", System.currentTimeMillis());

        return mockData;
    }

    /**
     * Get user permissions for a specific resource
     */
    private List<String> getUserResourcePermissions(
        UserContext userContext,
        String resourceId
    ) {
        // In a real implementation, this would fetch actual permissions
        List<String> permissions = new ArrayList<>();
        permissions.add("read");
        permissions.add("write");

        return permissions;
    }
}
