package open.ipaas.resourcemanager.controller;

import open.ipaas.resourcemanager.service.ResourceService;
import open.ipaas.resourcemanager.dto.ResourceRequest;
import open.ipaas.resourcemanager.dto.ResourceResponse;
import open.ipaas.resourcemanager.dto.ResourceTypeRequest;
import open.ipaas.resourcemanager.model.Resource;
import open.ipaas.resourcemanager.model.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resources")
@CrossOrigin(origins = "*")
public class ResourceController {
    
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    
    @Autowired
    private ResourceService resourceService;

    @PostMapping
    public ResponseEntity<ResourceResponse> createResource(@RequestBody ResourceRequest request) {
        logger.info("Received request to create resource: {}", request.getName());
        
        try {
            Resource resource = resourceService.createResource(request);
            ResourceResponse response = ResourceResponse.fromResource(resource);
            logger.info("Successfully created resource with ID: {}", resource.getId());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating resource", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponse> getResourceById(@PathVariable String id) {
        logger.info("Received request to get resource by ID: {}", id);
        
        Resource resource = resourceService.getResourceById(id);
        if (resource != null) {
            ResourceResponse response = ResourceResponse.fromResource(resource);
            logger.debug("Successfully retrieved resource with ID: {}", id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            logger.warn("Resource with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceResponse> updateResource(@PathVariable String id, @RequestBody ResourceRequest request) {
        logger.info("Received request to update resource with ID: {}", id);
        
        try {
            Resource updatedResource = resourceService.updateResource(id, request);
            if (updatedResource != null) {
                ResourceResponse response = ResourceResponse.fromResource(updatedResource);
                logger.info("Successfully updated resource with ID: {}", id);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                logger.warn("Resource with ID {} not found for update", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating resource with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable String id) {
        logger.info("Received request to delete resource with ID: {}", id);
        
        boolean deleted = resourceService.deleteResource(id);
        if (deleted) {
            logger.info("Successfully deleted resource with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.warn("Resource with ID {} not found for deletion", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ResourceResponse>> getAllResources(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String ownerId,
            @RequestParam(required = false) String parentId) {
        logger.info("Received request to get all resources with filters - type: {}, ownerId: {}, parentId: {}", type, ownerId, parentId);
        
        List<Resource> resources = resourceService.getAllResources(type, ownerId, parentId);
        List<ResourceResponse> responses = resources.stream()
            .map(ResourceResponse::fromResource)
            .collect(Collectors.toList());
            
        logger.debug("Returning {} resources", responses.size());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{parentId}/children")
    public ResponseEntity<List<ResourceResponse>> getChildResources(@PathVariable String parentId) {
        logger.info("Received request to get child resources for parent ID: {}", parentId);
        
        List<Resource> childResources = resourceService.getChildResources(parentId);
        List<ResourceResponse> responses = childResources.stream()
            .map(ResourceResponse::fromResource)
            .collect(Collectors.toList());
            
        logger.debug("Returning {} child resources for parent ID: {}", responses.size(), parentId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    // Resource Type Registry endpoints
    @GetMapping("/types")
    public ResponseEntity<List<ResourceType>> getAllResourceTypes() {
        logger.info("Received request to get all resource types");
        
        List<ResourceType> resourceTypes = resourceService.getAllResourceTypes();
        logger.debug("Returning {} resource types", resourceTypes.size());
        return new ResponseEntity<>(resourceTypes, HttpStatus.OK);
    }

    @PostMapping("/types")
    public ResponseEntity<ResourceType> createResourceType(@RequestBody ResourceTypeRequest request) {
        logger.info("Received request to create resource type: {}", request.getName());
        
        try {
            ResourceType resourceType = resourceService.createResourceType(request);
            logger.info("Successfully created resource type with ID: {}", resourceType.getId());
            return new ResponseEntity<>(resourceType, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating resource type", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/types/{typeId}")
    public ResponseEntity<ResourceType> getResourceTypeById(@PathVariable String typeId) {
        logger.info("Received request to get resource type by ID: {}", typeId);
        
        ResourceType resourceType = resourceService.getResourceTypeById(typeId);
        if (resourceType != null) {
            logger.debug("Successfully retrieved resource type with ID: {}", typeId);
            return new ResponseEntity<>(resourceType, HttpStatus.OK);
        } else {
            logger.warn("Resource type with ID {} not found", typeId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}