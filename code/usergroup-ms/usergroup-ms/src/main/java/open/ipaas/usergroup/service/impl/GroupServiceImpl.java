package open.ipaas.usergroup.service.impl;

import open.ipaas.usergroup.model.Group;
import open.ipaas.usergroup.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GroupServiceImpl implements GroupService {

    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final Map<String, Group> groupStorage = new ConcurrentHashMap<>();

    @Override
    public List<Group> getAllGroups() {
        logger.info("Fetching all groups");
        return new ArrayList<>(groupStorage.values());
    }

    @Override
    public Optional<Group> getGroupById(String id) {
        logger.info("Fetching group with id: {}", id);
        Group group = groupStorage.get(id);
        if (group != null) {
            logger.debug("Found group: {}", group.getName());
        } else {
            logger.warn("Group with id {} not found", id);
        }
        return Optional.ofNullable(group);
    }

    @Override
    public Group createGroup(Group group) {
        logger.info("Creating group with name: {}", group.getName());

        if (group.getId() == null || group.getId().isEmpty()) {
            group.setId(UUID.randomUUID().toString());
        }

        groupStorage.put(group.getId(), group);
        logger.info("Successfully created group with id: {}", group.getId());
        return group;
    }

    @Override
    public Group updateGroup(String id, Group groupDetails) {
        logger.info("Updating group with id: {}", id);

        Group group = groupStorage.get(id);
        if (group != null) {
            group.setName(groupDetails.getName());
            group.setDescription(groupDetails.getDescription());
            group.setParentId(groupDetails.getParentId());

            groupStorage.put(id, group);
            logger.info("Successfully updated group with id: {}", id);
        } else {
            logger.warn("Attempted to update non-existent group with id: {}", id);
            throw new RuntimeException("Group not found with id: " + id);
        }

        return group;
    }

    @Override
    public void deleteGroup(String id) {
        logger.info("Deleting group with id: {}", id);

        Group removedGroup = groupStorage.remove(id);
        if (removedGroup != null) {
            logger.info("Successfully deleted group with id: {}", id);
        } else {
            logger.warn("Attempted to delete non-existent group with id: {}", id);
            throw new RuntimeException("Group not found with id: " + id);
        }
    }

    @Override
    public Group addUserToGroup(String groupId, String userId) {
        logger.info("Adding user {} to group {}", userId, groupId);

        Group group = groupStorage.get(groupId);
        if (group != null) {
            // In a real implementation, we would validate that the user exists
            // For now, we'll just log the action
            logger.info("Added user {} to group {}", userId, groupId);
            return group;
        } else {
            logger.warn("Attempted to add user to non-existent group with id: {}", groupId);
            throw new RuntimeException("Group not found with id: " + groupId);
        }
    }

    @Override
    public Group removeUserFromGroup(String groupId, String userId) {
        logger.info("Removing user {} from group {}", userId, groupId);

        Group group = groupStorage.get(groupId);
        if (group != null) {
            // In a real implementation, we would remove the user from the group
            // For now, we'll just log the action
            logger.info("Removed user {} from group {}", userId, groupId);
            return group;
        } else {
            logger.warn("Attempted to remove user from non-existent group with id: {}", groupId);
            throw new RuntimeException("Group not found with id: " + groupId);
        }
    }
}
