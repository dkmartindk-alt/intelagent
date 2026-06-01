package open.ipaas.usergroup.controller;

import open.ipaas.usergroup.model.User;
import open.ipaas.usergroup.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Received request to get all users");
        List<User> users = userService.getAllUsers();
        logger.info("Returning {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        logger.info("Received request to get user with id: {}", id);
        Optional<User> user = userService.getUserById(id);

        if (user.isPresent()) {
            logger.debug("Returning user: {}", user.get().getUsername());
            return ResponseEntity.ok(user.get());
        } else {
            logger.warn("User with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("Received request to create user with username: {}", user.getUsername());
        User createdUser = userService.createUser(user);
        logger.info("Successfully created user with id: {}", createdUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User userDetails) {
        logger.info("Received request to update user with id: {}", id);
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            logger.info("Successfully updated user with id: {}", id);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            logger.error("Error updating user with id {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        logger.info("Received request to delete user with id: {}", id);
        try {
            userService.deleteUser(id);
            logger.info("Successfully deleted user with id: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Error deleting user with id {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
