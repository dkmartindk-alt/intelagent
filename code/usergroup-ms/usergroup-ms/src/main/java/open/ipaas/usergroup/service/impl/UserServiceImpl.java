package open.ipaas.usergroup.service.impl;

import open.ipaas.usergroup.model.User;
import open.ipaas.usergroup.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final Map<String, User> userStorage = new ConcurrentHashMap<>();

    @Override
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return new ArrayList<>(userStorage.values());
    }

    @Override
    public Optional<User> getUserById(String id) {
        logger.info("Fetching user with id: {}", id);
        User user = userStorage.get(id);
        if (user != null) {
            logger.debug("Found user: {}", user.getUsername());
        } else {
            logger.warn("User with id {} not found", id);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public User createUser(User user) {
        logger.info("Creating user with username: {}", user.getUsername());

        if (user.getId() == null || user.getId().isEmpty()) {
            user.setId(UUID.randomUUID().toString());
        }

        userStorage.put(user.getId(), user);
        logger.info("Successfully created user with id: {}", user.getId());
        return user;
    }

    @Override
    public User updateUser(String id, User userDetails) {
        logger.info("Updating user with id: {}", id);

        User user = userStorage.get(id);
        if (user != null) {
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setRoleId(userDetails.getRoleId());
            user.setGroupId(userDetails.getGroupId());

            userStorage.put(id, user);
            logger.info("Successfully updated user with id: {}", id);
        } else {
            logger.warn("Attempted to update non-existent user with id: {}", id);
            throw new RuntimeException("User not found with id: " + id);
        }

        return user;
    }

    @Override
    public void deleteUser(String id) {
        logger.info("Deleting user with id: {}", id);

        User removedUser = userStorage.remove(id);
        if (removedUser != null) {
            logger.info("Successfully deleted user with id: {}", id);
        } else {
            logger.warn("Attempted to delete non-existent user with id: {}", id);
            throw new RuntimeException("User not found with id: " + id);
        }
    }
}
