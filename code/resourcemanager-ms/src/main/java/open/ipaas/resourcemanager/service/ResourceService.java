package open.ipaas.resourcemanager.service;

import open.ipaas.resourcemanager.model.Resource;
import open.ipaas.resourcemanager.model.ResourceType;
import open.ipaas.resourcemanager.model.ResourceRelationship;
import open.ipaas.resourcemanager.dto.ResourceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ResourceService {
    
    private static final Logger logger = LoggerFactory.getLogger(ResourceService.class);
    
    // In-memory storage - in a real application, this would be replaced with database persistence
    private final Map<String, Resource> resources = new ConcurrentHashMap<>();
    private final Map<String, ResourceType> resourceTypes = new ConcurrentHashMap<>();
    private final Map<String, ResourceRelationship> relationships = new ConcurrentHashMap<>();
    
    // Counter for generating IDs
    private int resourceIdCounter = 1;
    private int resourceTypeIdCounter = 1;
    private int relationshipIdCounter = 1;

    public Resource createResource(ResourceRequest request) {
        logger.info("Creating resource with name: {}", request.getName());
        
        String id = "resource-" + resourceIdCounter++;
        
        Resource resource = new Resource();
        resource.setId(id);
        resource.setName(request.getName());
        resource.setType(request.getType());
        resource.setParentId(request.getParentId());
        resource.setMetadata(request.getMetadata());
        resource.setCreatedAt(LocalDateTime.now());
        resource.setUpdatedAt(LocalDateTime.now());
        resource.setInheritsSecurity(request.getInheritsSecurity());
        resource.setOwnerGroupId(request.getOwnerGroupId());
        
        resources.put(id, resource);
        
        // If there's a parent, create a relationship
        if (request.getParentId() != null && !request.getParentId().isEmpty()) {
            createRelationship(request.getParentId(), id, "CONTAINS");
        }
        
        logger.info("Created resource with ID: {}", id);
        return resource;
    }

    public Resource getResourceById(String id) {
        logger.debug("Fetching resource with ID: {}", id);
        return resources.get(id);
    }

    public Resource updateResource(String id, ResourceRequest request) {
        logger.info("Updating resource with ID: {}", id);
        
        Resource existingResource = resources.get(id);
        if (existingResource == null) {
            logger.warn("Attempted to update non-existent resource with ID: {}", id);
            return null;
        }
        
        existingResource.setName(request.getName());
        existingResource.setType(request.getType());
        existingResource.setParentId(request.getParentId());
        existingResource.setMetadata(request.getMetadata());
        existingResource.setUpdatedAt(LocalDateTime.now());
        existingResource.setInheritsSecurity(request.getInheritsSecurity());
        existingResource.setOwnerGroupId(request.getOwnerGroupId());
        
        resources.put(id, existingResource);
        
        logger.info("Updated resource with ID: {}", id);
        return existingResource;
    }

    public boolean deleteResource(String id) {
        logger.info("Deleting resource with ID: {}", id);
        
        Resource resource = resources.remove(id);
        if (resource != null) {
            // Remove any relationships involving this resource
            relationships.values().removeIf(rel -> 
                rel.getParentId().equals(id) || rel.getChildId().equals(id));
            
            logger.info("Deleted resource with ID: {}", id);
            return true;
        } else {
            logger.warn("Attempted to delete non-existent resource with ID: {}", id);
            return false;
        }
    }

    public List<Resource> getAllResources(String type, String ownerId, String parentId) {
        logger.debug("Fetching resources with filters - type: {}, ownerId: {}, parentId: {}", type, ownerId, parentId);
        
        return resources.values().stream()
            .filter(resource -> type == null || type.isEmpty() || resource.getType().equals(type))
            .filter(resource -> ownerId == null || ownerId.isEmpty() || 
                                (resource.getOwnerGroupId() != null && resource.getOwnerGroupId().equals(ownerId)))
            .filter(resource -> parentId == null || parentId.isEmpty() || 
                                (resource.getParentId() != null && resource.getParentId().equals(parentId)))
            .collect(Collectors.toList());
    }

    public List<Resource> getChildResources(String parentId) {
        logger.debug("Fetching child resources for parent ID: {}", parentId);
        
        return relationships.values().stream()
            .filter(rel -> rel.getParentId().equals(parentId))
            .map(rel -> resources.get(rel.getChildId()))
            .filter(Objects::nonNull) // Filter out null resources
            .collect(Collectors.toList());
    }

    public ResourceType createResourceType(ResourceTypeRequest request) {
        logger.info("Creating resource type with name: {}", request.getName());
        
        String id = "rtype-" + resourceTypeIdCounter++;
        
        ResourceType resourceType = new ResourceType();
        resourceType.setId(id);
        resourceType.setName(request.getName());
        resourceType.setServiceEndpoint(request.getServiceEndpoint());
        resourceType.setDescription(request.getDescription());
        
        resourceTypes.put(id, resourceType);
        
        logger.info("Created resource type with ID: {}", id);
        return resourceType;
    }

    public List<ResourceType> getAllResourceTypes() {
        logger.debug("Fetching all resource types");
        return new ArrayList<>(resourceTypes.values());
    }

    public ResourceType getResourceTypeById(String id) {
        logger.debug("Fetching resource type with ID: {}", id);
        return resourceTypes.get(id);
    }

    public ResourceRelationship createRelationship(String parentId, String childId, String relationshipType) {
        logger.info("Creating relationship: {} -> {} ({})", parentId, childId, relationshipType);
        
        String id = "rel-" + relationshipIdCounter++;
        
        ResourceRelationship relationship = new ResourceRelationship();
        relationship.setParentId(parentId);
        relationship.setChildId(childId);
        relationship.setRelationshipType(relationshipType);
        
        relationships.put(id, relationship);
        
        logger.info("Created relationship with ID: {}", id);
        return relationship;
    }

    public List<Resource> getResourcesByParentId(String parentId) {
        logger.debug("Fetching resources with parent ID: {}", parentId);
        
        return relationships.values().stream()
            .filter(rel -> rel.getParentId().equals(parentId))
            .map(rel -> resources.get(rel.getChildId()))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
}