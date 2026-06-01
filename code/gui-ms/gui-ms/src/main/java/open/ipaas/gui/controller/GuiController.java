package open.ipaas.gui.controller;

import open.ipaas.gui.model.Resource;
import open.ipaas.gui.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
@Tag(name = "GUI Controller", description = "Resource management and navigation endpoints")
public class GuiController {

    private static final Logger logger = LoggerFactory.getLogger(GuiController.class);

    // In-memory storage for demonstration purposes
    private List<Resource> resources = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public GuiController() {
        // Initialize with some sample data
        User adminUser = new User("1", "admin", "admin@example.com", "ADMIN");
        users.add(adminUser);

        Resource rootFolder = new Resource(
            "root-001",
            "Root Folder",
            "FOLDER",
            null,
            LocalDateTime.now(),
            LocalDateTime.now(),
            true,
            "admin-group"
        );
        resources.add(rootFolder);
    }

    @GetMapping("/")
    @Operation(summary = "Main dashboard", description = "Returns the main dashboard view")
    public String getDashboard() {
        logger.info("Accessing main dashboard");
        return "Welcome to the GUI Dashboard";
    }

    @GetMapping("/resources/{id}")
    @Operation(summary = "Get resource details", description = "Retrieves details for a specific resource")
    public Resource getResource(@PathVariable String id) {
        logger.info("Getting resource with id: {}", id);
        return resources.stream()
                .filter(resource -> resource.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Resource not found with id: " + id));
    }

    @GetMapping("/resources/{id}/contents")
    @Operation(summary = "Get contents of a folder-type resource", description = "Retrieves the contents of a folder-type resource")
    public List<Resource> getResourceContents(@PathVariable String id) {
        logger.info("Getting contents of resource with id: {}", id);
        return resources.stream()
                .filter(resource -> id.equals(resource.getParentId()))
                .toList();
    }

    @PostMapping("/resources")
    @Operation(summary = "Create resource (folder)", description = "Creates a new resource (folder)")
    public Resource createResource(@RequestBody Resource resource) {
        logger.info("Creating new resource: {}", resource.getName());

        // Generate a new ID if not provided
        if (resource.getId() == null || resource.getId().isEmpty()) {
            resource.setId(UUID.randomUUID().toString());
        }

        // Set timestamps
        resource.setCreatedAt(LocalDateTime.now());
        resource.setUpdatedAt(LocalDateTime.now());

        // Default values if not set
        if (resource.getType() == null) {
            resource.setType("FOLDER");
        }
        if (resource.getInheritsSecurity() == null) {
            resource.setInheritsSecurity(true);
        }

        resources.add(resource);
        return resource;
    }

    @DeleteMapping("/resources/{id}")
    @Operation(summary = "Delete resource (folder)", description = "Deletes a resource (folder)")
    public String deleteResource(@PathVariable String id) {
        logger.info("Deleting resource with id: {}", id);
        boolean removed = resources.removeIf(resource -> resource.getId().equals(id));
        if (!removed) {
            throw new RuntimeException("Resource not found with id: " + id);
        }
        return "Resource deleted successfully";
    }

    @GetMapping("/browse/{folderId}")
    @Operation(summary = "Browse folder contents", description = "Browses the contents of a specific folder")
    public List<Resource> browseFolder(@PathVariable String folderId) {
        logger.info("Browsing folder with id: {}", folderId);
        return resources.stream()
                .filter(resource -> folderId.equals(resource.getParentId()))
                .toList();
    }
}
