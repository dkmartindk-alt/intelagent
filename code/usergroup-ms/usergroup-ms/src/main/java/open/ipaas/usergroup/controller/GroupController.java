package open.ipaas.usergroup.controller;

import open.ipaas.usergroup.model.Group;
import open.ipaas.usergroup.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/groups")
@CrossOrigin(origins = "*")
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        logger.info("Received request to get all groups");
        List<Group> groups = groupService.getAllGroups();
        logger.info("Returning {} groups", groups.size());
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable String id) {
        logger.info("Received request to get group with id: {}", id);
        Optional<Group> group = groupService.getGroupById(id);

        if (group.isPresent()) {
            logger.debug("Returning group: {}", group.get().getName());
            return ResponseEntity.ok(group.get());
        } else {
            logger.warn("Group with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        logger.info("Received request to create group with name: {}", group.getName());
        Group createdGroup = groupService.createGroup(group);
        logger.info("Successfully created group with id: {}", createdGroup.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable String id, @RequestBody Group groupDetails) {
        logger.info("Received request to update group with id: {}", id);
        try {
            Group updatedGroup = groupService.updateGroup(id, groupDetails);
            logger.info("Successfully updated group with id: {}", id);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            logger.error("Error updating group with id {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable String id) {
        logger.info("Received request to delete group with id: {}", id);
        try {
            groupService.deleteGroup(id);
            logger.info("Successfully deleted group with id: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Error deleting group with id {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/add-user")
    public ResponseEntity<Group> addUserToGroup(@PathVariable String id, @RequestParam String userId) {
        logger.info("Received request to add user {} to group {}", userId, id);
        try {
            Group updatedGroup = groupService.addUserToGroup(id, userId);
            logger.info("Successfully added user {} to group {}", userId, id);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            logger.error("Error adding user {} to group {}: {}", userId, id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/remove-user")
    public ResponseEntity<Group> removeUserFromGroup(@PathVariable String id, @RequestParam String userId) {
        logger.info("Received request to remove user {} from group {}", userId, id);
        try {
            Group updatedGroup = groupService.removeUserFromGroup(id, userId);
            logger.info("Successfully removed user {} from group {}", userId, id);
            return ResponseEntity.ok(updatedGroup);
        } catch (RuntimeException e) {
            logger.error("Error removing user {} from group {}: {}", userId, id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
