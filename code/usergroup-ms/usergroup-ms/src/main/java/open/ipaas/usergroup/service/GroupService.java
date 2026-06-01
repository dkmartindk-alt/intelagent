package open.ipaas.usergroup.service;

import open.ipaas.usergroup.model.Group;
import java.util.List;
import java.util.Optional;

public interface GroupService {
    List<Group> getAllGroups();
    Optional<Group> getGroupById(String id);
    Group createGroup(Group group);
    Group updateGroup(String id, Group group);
    void deleteGroup(String id);
    Group addUserToGroup(String groupId, String userId);
    Group removeUserFromGroup(String groupId, String userId);
}
