package open.ipaas.controller.controller;

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
@RequestMapping("/context")
@Tag(
    name = "User Context",
    description = "Endpoints for managing user context with groups"
)
public class ContextController {

    private static final Logger logger = LoggerFactory.getLogger(
        ContextController.class
    );

    @Autowired
    private ResourceService resourceService;

    @Operation(
        summary = "Get user context with groups",
        description = "Retrieve user context information including groups and permissions"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved user context"
    )
    @PostMapping("/user/{userId}")
    public ResponseEntity<UserContext> getUserContext(
        @Parameter(
            description = "ID of the user",
            required = true
        ) @PathVariable String userId
    ) {

        logger.info("Request to get context for user: {}", userId);

        UserContext userContext = resourceService.getUserContext(userId);

        logger.info("Successfully retrieved context for user: {}", userId);

        return ResponseEntity.ok(userContext);
    }
}
