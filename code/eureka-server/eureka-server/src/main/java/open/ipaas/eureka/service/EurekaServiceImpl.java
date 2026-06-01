package open.ipaas.eureka.service;

import open.ipaas.eureka.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class EurekaServiceImpl implements EurekaService {

    private static final Logger logger = LoggerFactory.getLogger(EurekaServiceImpl.class);

    // In-memory storage for service instances
    private final Map<String, Map<String, ServiceInstance>> registry = new ConcurrentHashMap<>();

    @Override
    public void registerService(String appName, ServiceInstance instance) {
        logger.info("Registering service: {} with instance ID: {}", appName, instance.getInstanceId());

        registry.computeIfAbsent(appName, k -> new ConcurrentHashMap<>())
                .put(instance.getInstanceId(), instance);

        logger.debug("Service {} with instance ID {} registered successfully", appName, instance.getInstanceId());
    }

    @Override
    public void deregisterService(String appName, String instanceId) {
        logger.info("Deregistering service: {} with instance ID: {}", appName, instanceId);

        Map<String, ServiceInstance> appInstances = registry.get(appName);
        if (appInstances != null) {
            ServiceInstance removedInstance = appInstances.remove(instanceId);
            if (removedInstance != null) {
                logger.info("Service {} with instance ID {} deregistered successfully", appName, instanceId);
            } else {
                logger.warn("Attempted to deregister non-existent instance {} from app {}", instanceId, appName);
            }

            // Clean up app entry if no instances remain
            if (appInstances.isEmpty()) {
                registry.remove(appName);
            }
        } else {
            logger.warn("Attempted to deregister from non-existent app: {}", appName);
        }
    }

    @Override
    public void updateStatus(String appName, String instanceId, String status) {
        logger.info("Updating status for service: {} with instance ID: {} to status: {}",
                   appName, instanceId, status);

        Map<String, ServiceInstance> appInstances = registry.get(appName);
        if (appInstances != null) {
            ServiceInstance instance = appInstances.get(instanceId);
            if (instance != null) {
                instance.setStatus(status);
                logger.info("Status updated for service {} with instance ID {} to: {}",
                           appName, instanceId, status);
            } else {
                logger.warn("Attempted to update status for non-existent instance {} in app {}",
                           instanceId, appName);
            }
        } else {
            logger.warn("Attempted to update status for non-existent app: {}", appName);
        }
    }

    @Override
    public List<Application> getAllApplications() {
        logger.info("Fetching all applications");

        List<Application> applications = registry.entrySet().stream()
            .map(entry -> new Application(entry.getKey(),
                    new ArrayList<>(entry.getValue().values())))
            .collect(Collectors.toList());

        logger.debug("Returning {} applications", applications.size());
        return applications;
    }

    @Override
    public Optional<Application> getApplicationByName(String appName) {
        logger.info("Fetching application by name: {}", appName);

        Map<String, ServiceInstance> appInstances = registry.get(appName);
        if (appInstances != null) {
            List<ServiceInstance> instances = new ArrayList<>(appInstances.values());
            Application application = new Application(appName, instances);
            logger.debug("Found application {} with {} instances", appName, instances.size());
            return Optional.of(application);
        } else {
            logger.debug("Application {} not found", appName);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ServiceInstance> getServiceInstance(String appName, String instanceId) {
        logger.info("Fetching service instance: {} from application: {}", instanceId, appName);

        Map<String, ServiceInstance> appInstances = registry.get(appName);
        if (appInstances != null) {
            ServiceInstance instance = appInstances.get(instanceId);
            if (instance != null) {
                logger.debug("Found service instance: {} in application: {}", instanceId, appName);
                return Optional.of(instance);
            } else {
                logger.debug("Service instance {} not found in application: {}", instanceId, appName);
                return Optional.empty();
            }
        } else {
            logger.debug("Application {} not found", appName);
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getInstanceStatus(String appName, String instanceId) {
        logger.info("Fetching status for service instance: {} from application: {}", instanceId, appName);

        Optional<ServiceInstance> instanceOpt = getServiceInstance(appName, instanceId);
        if (instanceOpt.isPresent()) {
            String status = instanceOpt.get().getStatus();
            logger.debug("Status for instance {} in application {}: {}", instanceId, appName, status);
            return Optional.ofNullable(status);
        } else {
            logger.debug("Service instance {} not found in application: {}", instanceId, appName);
            return Optional.empty();
        }
    }

    @Override
    public boolean isHealthy(String appName, String instanceId) {
        logger.info("Checking health for service instance: {} from application: {}", instanceId, appName);

        Optional<String> statusOpt = getInstanceStatus(appName, instanceId);
        if (statusOpt.isPresent()) {
            String status = statusOpt.get();
            boolean isHealthy = "UP".equalsIgnoreCase(status);
            logger.debug("Health check for instance {} in application {}: {}",
                        instanceId, appName, isHealthy ? "HEALTHY" : "UNHEALTHY");
            return isHealthy;
        } else {
            logger.debug("Cannot check health - service instance {} not found in application: {}",
                        instanceId, appName);
            return false;
        }
    }
}
