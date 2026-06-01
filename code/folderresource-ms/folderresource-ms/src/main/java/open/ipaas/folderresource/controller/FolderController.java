package open.ipaas.folderresource.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import open.ipaas.folderresource.model.Folder;
import open.ipaas.folderresource.model.FolderContent;
import open.ipaas.folderresource.model.Resource;
import open.ipaas.folderresource.service.FolderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/folders")
@Tag(name = "Folder Management", description = "Operations related to folder management")
public class FolderController {

    private static final Logger logger = LoggerFactory.getLogger(FolderController.class);

    @Autowired
    private FolderService folderService;

    @Operation(summary = "Create a new folder", description = "Creates a new folder resource")
    @PostMapping
    public ResponseEntity<Folder> createFolder(@RequestBody Folder folder) {
        logger.info("Received request to create folder: {}", folder.getName());
        try {
            Folder createdFolder = folderService.createFolder(folder);
            logger.info("Successfully created folder with ID: {}", createdFolder.getId());
            return new ResponseEntity<>(createdFolder, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating folder: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get folder by ID", description = "Retrieves folder details by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<Folder> getFolderById(@PathVariable String id) {
        logger.debug("Received request to get folder with ID: {}", id);
        try {
            Folder folder = folderService.getFolderById(id);
            logger.debug("Successfully retrieved folder with ID: {}", id);
            return new ResponseEntity<>(folder, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving folder with ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update folder", description = "Updates an existing folder by its ID")
    @PutMapping("/{id}")
    public ResponseEntity<Folder> updateFolder(@PathVariable String id, @RequestBody Folder folder) {
        logger.info("Received request to update folder with ID: {}", id);
        try {
            Folder updatedFolder = folderService.updateFolder(id, folder);
            logger.info("Successfully updated folder with ID: {}", id);
            return new ResponseEntity<>(updatedFolder, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating folder with ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete folder", description = "Deletes a folder by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFolder(@PathVariable String id) {
        logger.info("Received request to delete folder with ID: {}", id);
        try {
            folderService.deleteFolder(id);
            logger.info("Successfully deleted folder with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting folder with ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "List all folders", description = "Retrieves a list of all folders")
    @GetMapping
    public ResponseEntity<List<Folder>> getAllFolders() {
        logger.debug("Received request to get all folders");
        try {
            List<Folder> folders = folderService.getAllFolders();
            logger.debug("Successfully retrieved {} folders", folders.size());
            return new ResponseEntity<>(folders, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving all folders: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get folder contents", description = "Retrieves the contents of a folder")
    @GetMapping("/{id}/contents")
    public ResponseEntity<FolderContent> getFolderContents(@PathVariable String id) {
        logger.debug("Received request to get contents for folder ID: {}", id);
        try {
            FolderContent content = folderService.getFolderContents(id);
            logger.debug("Successfully retrieved contents for folder ID: {}", id);
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving contents for folder ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add resource to folder", description = "Adds a resource to a folder")
    @PostMapping("/{id}/add-resource")
    public ResponseEntity<Folder> addResourceToFolder(@PathVariable String id, @RequestBody Resource resource) {
        logger.info("Received request to add resource {} to folder ID: {}", resource.getName(), id);
        try {
            Folder folder = folderService.addResourceToFolder(id, resource);
            logger.info("Successfully added resource to folder ID: {}", id);
            return new ResponseEntity<>(folder, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error adding resource to folder ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Remove resource from folder", description = "Removes a resource from a folder")
    @PostMapping("/{id}/remove-resource")
    public ResponseEntity<Folder> removeResourceFromFolder(@PathVariable String id, @RequestBody Map<String, String> requestBody) {
        String resourceId = requestBody.get("resourceId");
        logger.info("Received request to remove resource ID {} from folder ID: {}", resourceId, id);
        try {
            Folder folder = folderService.removeResourceFromFolder(id, resourceId);
            logger.info("Successfully removed resource ID {} from folder ID: {}", resourceId, id);
            return new ResponseEntity<>(folder, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error removing resource from folder ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get folder hierarchy", description = "Retrieves the hierarchical structure of a folder")
    @GetMapping("/{id}/hierarchy")
    public ResponseEntity<Map<String, Object>> getFolderHierarchy(@PathVariable String id) {
        logger.debug("Received request to get hierarchy for folder ID: {}", id);
        try {
            Map<String, Object> hierarchy = folderService.getFolderHierarchy(id);
            logger.debug("Successfully retrieved hierarchy for folder ID: {}", id);
            return new ResponseEntity<>(hierarchy, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving hierarchy for folder ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
