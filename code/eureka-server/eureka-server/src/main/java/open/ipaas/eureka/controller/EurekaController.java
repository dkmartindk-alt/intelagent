package open.ipaas.eureka.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import open.ipaas.eureka.model.*;
import open.ipaas.eureka.service.EurekaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/eureka")
@Tag(name = "Eureka Service Registry", description = "Endpoints for service registration and discovery")
public class EurekaController {

    private static final Logger logger = LoggerFactory.getLogger(EurekaController.class);

    @Autowired
    private EurekaService eurekaService;

    // Service Registration Endpoints
    @Operation(summary = "Register a new service instance", description = "Registers a new service instance with the Eureka server")
    @PostMapping("/apps/{appName}")
    public ResponseEntity<Void> registerService(
            @Parameter(description = "Application name", required = true) @PathVariable String appName,
            @RequestBody RegistrationRequest request) {
        logger.info("Received registration request for app: {}, instance: {}",
                   appName, request.getInstance().getInstanceId());

        eurekaService.registerService(appName, request.getInstance());
        logger.info("Successfully registered service: {} with instance: {}",
                   appName, request.getInstance().getInstanceId());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "De-register a service instance", description = "Removes a service instance from the registry")
    @DeleteMapping("/apps/{appName}/{instanceId}")
    public ResponseEntity<Void> deregisterService(
            @Parameter(description = "Application name", required = true) @PathVariable String appName,
            @Parameter(description = "Instance ID", required = true) @PathVariable String instanceId) {
        logger.info("Received de-registration request for app: {}, instance: {}", appName, instanceId);

        eurekaService.deregisterService(appName, instanceId);
        logger.info("Successfully deregistered service: {} with instance: {}", appName, instanceId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update service instance status", description = "Updates the status of a registered service instance")
    @PutMapping("/apps/{appName}/{instanceId}")
    public ResponseEntity<Void> updateServiceStatus(
            @Parameter(description = "Application name", required = true) @PathVariable String appName,
            @Parameter(description = "Instance ID", required = true) @PathVariable String instanceId,
            @RequestParam String status) {
        logger.info("Received status update request for app: {}, instance: {}, new status: {}",
                   appName, instanceId, status);

        eurekaService.updateStatus(appName, instanceId, status);
        logger.info("Successfully updated status for service: {} with instance: {} to: {}",
                   appName, instanceId, status);

        return ResponseEntity.ok().build();
    }

    // Service Discovery Endpoints
    @Operation(summary = "Get all registered applications", description = "Returns all applications registered with the Eureka server")
    @GetMapping("/apps")
    public ResponseEntity<List<Application>> getAllApplications() {
        logger.info("Received request to get all applications");

        List<Application> applications = eurekaService.getAllApplications();
        logger.info("Returning {} applications", applications.size());

        return ResponseEntity.ok(applications);
    }

    @Operation(summary = "Get all instances of a specific application", description = "Returns all instances of the specified application")
    @GetMapping("/apps/{appName}")
    public ResponseEntity<Application> getApplication(
            @Parameter(description = "Application name", required = true) @PathVariable String appName) {
        logger.info("Received request to get application: {}", appName);

        Optional<Application> application = eurekaService.getApplicationByName(appName);
        if (application.isPresent()) {
            logger.info("Found application: {} with {} instances", appName,
                       application.get().getInstances().size());
            return ResponseEntity.ok(application.get());
        } else {
            logger.warn("Application {} not found", appName);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get a specific service instance", description = "Returns a specific service instance by application name and instance ID")
    @GetMapping("/apps/{appName}/{instanceId}")
    public ResponseEntity<ServiceInstance> getServiceInstance(
            @Parameter(description = "Application name", required = true) @PathVariable String appName,
            @Parameter(description = "Instance ID", required = true) @PathVariable String instanceId) {
        logger.info("Received request to get service instance: {} from application: {}", instanceId, appName);

        Optional<ServiceInstance> instance = eurekaService.getServiceInstance(appName, instanceId);
        if (instance.isPresent()) {
            logger.info("Found service instance: {}", instanceId);
            return ResponseEntity.ok(instance.get());
        } else {
            logger.warn("Service instance {} not found in application: {}", instanceId, appName);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get applications by VIP address", description = "Returns applications registered with the specified VIP address")
    @GetMapping("/vips/{vipAddress}")
    public ResponseEntity<List<Application>> getApplicationsByVipAddress(
            @Parameter(description = "VIP address", required = true) @PathVariable String vipAddress) {
        logger.info("Received request to get applications by VIP address: {}", vipAddress);

        // Filter applications by VIP address
        List<Application> allApplications = eurekaService.getAllApplications();
        List<Application> filteredApplications = allApplications.stream()
            .filter(app -> app.getInstances().stream()
                .anyMatch(instance -> vipAddress.equals(instance.getVipAddress())))
            .toList();

        logger.info("Found {} applications with VIP address: {}", filteredApplications.size(), vipAddress);
        return ResponseEntity.ok(filteredApplications);
    }

    @Operation(summary = "Get applications by secure VIP address", description = "Returns applications registered with the specified secure VIP address")
    @GetMapping("/svips/{svipAddress}")
    public ResponseEntity<List<Application>> getApplicationsBySecureVipAddress(
            @Parameter(description = "Secure VIP address", required = true) @PathVariable String svipAddress) {
        logger.info("Received request to get applications by secure VIP address: {}", svipAddress);

        // Filter applications by secure VIP address
        List<Application> allApplications = eurekaService.getAllApplications();
        List<Application> filteredApplications = allApplications.stream()
            .filter(app -> app.getInstances().stream()
                .anyMatch(instance -> svipAddress.equals(instance.getSecureVipAddress())))
            .toList();

        logger.info("Found {} applications with secure VIP address: {}", filteredApplications.size(), svipAddress);
        return ResponseEntity.ok(filteredApplications);
    }

    // Health Check Endpoints
    @Operation(summary = "Get instance status", description = "Returns the status of a specific service instance")
    @GetMapping("/apps/{appName}/{instanceId}/status")
    public ResponseEntity<String> getInstanceStatus(
            @Parameter(description = "Application name", required = true) @PathVariable String appName,
            @Parameter(description = "Instance ID", required = true) @PathVariable String instanceId) {
        logger.info("Received request to get status for service instance: {} from application: {}",
                   instanceId, appName);

        Optional<String> status = eurekaService.getInstanceStatus(appName, instanceId);
        if (status.isPresent()) {
            logger.info("Status for instance {} in application {}: {}", instanceId, appName, status.get());
            return ResponseEntity.ok(status.get());
        } else {
            logger.warn("Service instance {} not found in application: {}", instanceId, appName);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Health check endpoint", description = "Performs a health check on a specific service instance")
    @GetMapping("/apps/{appName}/{instanceId}/health")
    public ResponseEntity<String> healthCheck(
            @Parameter(description = "Application name", required = true) @PathVariable String appName,
            @Parameter(description = "Instance ID", required = true) @PathVariable String instanceId) {
        logger.info("Received health check request for service instance: {} from application: {}",
                   instanceId, appName);

        boolean isHealthy = eurekaService.isHealthy(appName, instanceId);
        String healthStatus = isHealthy ? "UP" : "DOWN";

        logger.info("Health check result for instance {} in application {}: {}",
                   instanceId, appName, healthStatus);

        return ResponseEntity.ok(healthStatus);
    }
}
