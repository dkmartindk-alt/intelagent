package open.ipaas.folderresource.service;

import open.ipaas.folderresource.model.Folder;
import open.ipaas.folderresource.model.FolderContent;
import open.ipaas.folderresource.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class FolderService {

    private static final Logger logger = LoggerFactory.getLogger(FolderService.class);

    // In-memory storage for demonstration purposes
    private final Map<String, Folder> folders = new ConcurrentHashMap<>();
    private final Map<String, List<Resource>> folderContents = new ConcurrentHashMap<>();

    public FolderService() {
        // Initialize with a root folder
        Folder rootFolder = new Folder("root", "Root");
        rootFolder.setParentId(null);
        rootFolder.setOwnerGroupId("admin-group");
        rootFolder.setInheritsSecurity(false);
        folders.put("root", rootFolder);
        folderContents.put("root", new ArrayList<>());
        logger.info("FolderService initialized with root folder");
    }

    public Folder createFolder(Folder folder) {
        logger.info("Creating folder: {}", folder.getName());

        if (folder.getId() == null || folder.getId().isEmpty()) {
            folder.setId(UUID.randomUUID().toString());
        }

        if (folder.getCreatedAt() == null) {
            folder.setCreatedAt(LocalDateTime.now());
        }

        folder.setUpdatedAt(LocalDateTime.now());

        // Validate parent folder exists if parentId is provided
        if (folder.getParentId() != null && !folder.getParentId().isEmpty()) {
            if (!folders.containsKey(folder.getParentId())) {
                throw new IllegalArgumentException("Parent folder does not exist: " + folder.getParentId());
            }
        } else {
            // If no parent is specified, set to root
            folder.setParentId("root");
        }

        folders.put(folder.getId(), folder);
        folderContents.put(folder.getId(), new ArrayList<>());

        logger.info("Folder created successfully with ID: {}", folder.getId());
        return folder;
    }

    public Folder getFolderById(String id) {
        logger.debug("Retrieving folder with ID: {}", id);

        Folder folder = folders.get(id);
        if (folder == null) {
            logger.warn("Folder not found with ID: {}", id);
            throw new NoSuchElementException("Folder not found: " + id);
        }

        return folder;
    }

    public List<Folder> getAllFolders() {
        logger.debug("Retrieving all folders");
        return new ArrayList<>(folders.values());
    }

    public Folder updateFolder(String id, Folder updatedFolder) {
        logger.info("Updating folder with ID: {}", id);

        Folder existingFolder = folders.get(id);
        if (existingFolder == null) {
            logger.warn("Attempt to update non-existent folder with ID: {}", id);
            throw new NoSuchElementException("Folder not found: " + id);
        }

        // Update fields
        if (updatedFolder.getName() != null) {
            existingFolder.setName(updatedFolder.getName());
        }
        if (updatedFolder.getParentId() != null) {
            // Validate parent exists
            if (!updatedFolder.getParentId().equals(existingFolder.getParentId()) &&
                !folders.containsKey(updatedFolder.getParentId())) {
                throw new IllegalArgumentException("Parent folder does not exist: " + updatedFolder.getParentId());
            }
            existingFolder.setParentId(updatedFolder.getParentId());
        }
        if (updatedFolder.getInheritsSecurity() != null) {
            existingFolder.setInheritsSecurity(updatedFolder.getInheritsSecurity());
        }
        if (updatedFolder.getOwnerGroupId() != null) {
            existingFolder.setOwnerGroupId(updatedFolder.getOwnerGroupId());
        }
        if (updatedFolder.getProperties() != null) {
            existingFolder.setProperties(updatedFolder.getProperties());
        }

        existingFolder.setUpdatedAt(LocalDateTime.now());

        logger.info("Folder updated successfully with ID: {}", id);
        return existingFolder;
    }

    public void deleteFolder(String id) {
        logger.info("Deleting folder with ID: {}", id);

        Folder folder = folders.get(id);
        if (folder == null) {
            logger.warn("Attempt to delete non-existent folder with ID: {}", id);
            throw new NoSuchElementException("Folder not found: " + id);
        }

        // Check if folder has child folders
        List<Folder> childFolders = folders.values().stream()
            .filter(f -> id.equals(f.getParentId()))
            .collect(Collectors.toList());

        if (!childFolders.isEmpty()) {
            logger.warn("Cannot delete folder {} as it has {} child folders", id, childFolders.size());
            throw new IllegalStateException("Cannot delete folder with child folders");
        }

        // Remove the folder and its contents
        folders.remove(id);
        folderContents.remove(id);

        logger.info("Folder deleted successfully with ID: {}", id);
    }

    public FolderContent getFolderContents(String folderId) {
        logger.debug("Retrieving contents for folder ID: {}", folderId);

        if (!folderContents.containsKey(folderId)) {
            logger.warn("Folder not found with ID: {}", folderId);
            throw new NoSuchElementException("Folder not found: " + folderId);
        }

        List<Resource> contents = folderContents.get(folderId);
        FolderContent folderContent = new FolderContent(folderId, contents);

        logger.debug("Retrieved {} items for folder ID: {}", contents.size(), folderId);
        return folderContent;
    }

    public Folder addResourceToFolder(String folderId, Resource resource) {
        logger.info("Adding resource {} to folder ID: {}", resource.getName(), folderId);

        Folder folder = getFolderById(folderId);

        if (!folderContents.containsKey(folderId)) {
            folderContents.put(folderId, new ArrayList<>());
        }

        List<Resource> contents = folderContents.get(folderId);

        // Generate ID if not provided
        if (resource.getId() == null || resource.getId().isEmpty()) {
            resource.setId(UUID.randomUUID().toString());
        }

        resource.setFolderId(folderId);
        contents.add(resource);

        logger.info("Resource {} added to folder ID: {}", resource.getName(), folderId);
        return folder;
    }

    public Folder removeResourceFromFolder(String folderId, String resourceId) {
        logger.info("Removing resource ID {} from folder ID: {}", resourceId, folderId);

        Folder folder = getFolderById(folderId);

        if (!folderContents.containsKey(folderId)) {
            logger.warn("Folder contents not found for folder ID: {}", folderId);
            throw new NoSuchElementException("Folder contents not found: " + folderId);
        }

        List<Resource> contents = folderContents.get(folderId);
        boolean removed = contents.removeIf(resource -> resource.getId().equals(resourceId));

        if (!removed) {
            logger.warn("Resource ID {} not found in folder ID: {}", resourceId, folderId);
            throw new NoSuchElementException("Resource not found in folder: " + resourceId);
        }

        logger.info("Resource ID {} removed from folder ID: {}", resourceId, folderId);
        return folder;
    }

    public Map<String, Object> getFolderHierarchy(String folderId) {
        logger.debug("Getting hierarchy for folder ID: {}", folderId);

        Folder rootFolder = getFolderById(folderId);
        Map<String, Object> hierarchy = buildFolderHierarchy(rootFolder);

        logger.debug("Hierarchy retrieved for folder ID: {}", folderId);
        return hierarchy;
    }

    private Map<String, Object> buildFolderHierarchy(Folder folder) {
        Map<String, Object> node = new HashMap<>();
        node.put("id", folder.getId());
        node.put("name", folder.getName());
        node.put("parentId", folder.getParentId());

        // Get immediate child folders
        List<Folder> childFolders = folders.values().stream()
            .filter(f -> folder.getId().equals(f.getParentId()))
            .collect(Collectors.toList());

        List<Map<String, Object>> children = new ArrayList<>();
        for (Folder child : childFolders) {
            children.add(buildFolderHierarchy(child));
        }

        node.put("children", children);
        node.put("contentCount", folderContents.getOrDefault(folder.getId(), Collections.emptyList()).size());

        return node;
    }
}
