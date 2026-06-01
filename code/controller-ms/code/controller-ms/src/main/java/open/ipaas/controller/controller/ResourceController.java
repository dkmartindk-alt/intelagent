package open.ipaas.controller.controller;

import open.ipaas.controller.model.ResourceRequest;
import open.ipaas.controller.model.ResourceResponse;
import open.ipaas.controller.model.UserContext;
import open.ipaas.controller.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resources")
@Tag(
    name = "Resource Management",
    description = "Endpoints for managing resources with access control"
)
public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(
        ResourceController.class
    );

    @Autowired
    private ResourceService resourceService;

    @Operation(
        summary = "Get folder contents securely",
        description = "Retrieve contents of a resource/folder by ID"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved resource contents"
    )
    @GetMapping("/{id}/contents")
    public ResponseEntity<ResourceResponse> getResourceContents(
        @Parameter(
            description = "ID of the resource/folder",
            required = true
        ) @PathVariable String id,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {

        logger.info("Request to get contents of resource: {}", id);

        // Create a resource request for getting contents
        ResourceRequest request = new ResourceRequest();
        request.setResourceId(id);
        request.setAction("READ");

        // In a real scenario, user context would come from authentication
        UserContext userContext = resourceService.getUserContext("anonymous");
        request.setUserContext(userContext);

        ResourceResponse response = resourceService.processResourceRequest(
            request
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Get specific resource securely",
        description = "Retrieve a specific resource by ID"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved resource"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponse> getResource(
        @Parameter(
            description = "ID of the resource",
            required = true
        ) @PathVariable String id
    ) {

        logger.info("Request to get resource: {}", id);

        // Create a resource request for getting the resource
        ResourceRequest request = new ResourceRequest();
        request.setResourceId(id);
        request.setAction("READ");

        // In a real scenario, user context would come from authentication
        UserContext userContext = resourceService.getUserContext("anonymous");
        request.setUserContext(userContext);

        ResourceResponse response = resourceService.processResourceRequest(
            request
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Create resource through secure workflow",
        description = "Create a new resource"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Successfully created resource"
    )
    @PostMapping
    public ResponseEntity<ResourceResponse> createResource(
        @RequestBody ResourceRequest request
    ) {

        logger.info("Request to create resource: {}", request.getResourceId());

        // Set the action to CREATE (mapped to WRITE internally)
        request.setAction("WRITE");

        // In a real scenario, user context would come from authentication
        if (request.getUserContext() == null) {
            UserContext userContext = resourceService.getUserContext(
                "anonymous"
            );
            request.setUserContext(userContext);
        }

        ResourceResponse response = resourceService.processResourceRequest(
            request
        );

        return ResponseEntity.status(201).body(response);
    }

    @Operation(
        summary = "Update resource through secure workflow",
        description = "Update an existing resource"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully updated resource"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ResourceResponse> updateResource(
        @Parameter(
            description = "ID of the resource to update",
            required = true
        ) @PathVariable String id,
        @RequestBody ResourceRequest request
    ) {

        logger.info("Request to update resource: {}", id);

        request.setResourceId(id);
        request.setAction("WRITE");

        // In a real scenario, user context would come from authentication
        if (request.getUserContext() == null) {
            UserContext userContext = resourceService.getUserContext(
                "anonymous"
            );
            request.setUserContext(userContext);
        }

        ResourceResponse response = resourceService.processResourceRequest(
            request
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Delete resource through secure workflow",
        description = "Delete a resource"
    )
    @ApiResponse(
        responseCode = "204",
        description = "Successfully deleted resource"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(
        @Parameter(
            description = "ID of the resource to delete",
            required = true
        ) @PathVariable String id
    ) {

        logger.info("Request to delete resource: {}", id);

        // Create a resource request for deleting
        ResourceRequest request = new ResourceRequest();
        request.setResourceId(id);
        request.setAction("DELETE");

        // In a real scenario, user context would come from authentication
        UserContext userContext = resourceService.getUserContext("anonymous");
        request.setUserContext(userContext);

        resourceService.processResourceRequest(request);

        return ResponseEntity.noContent().build();
    }
}
