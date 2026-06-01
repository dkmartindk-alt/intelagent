package open.ipaas.eureka.service;

import open.ipaas.eureka.model.*;

import java.util.List;
import java.util.Optional;

public interface EurekaService {
    // Service Registration
    void registerService(String appName, ServiceInstance instance);
    void deregisterService(String appName, String instanceId);
    void updateStatus(String appName, String instanceId, String status);

    // Service Discovery
    List<Application> getAllApplications();
    Optional<Application> getApplicationByName(String appName);
    Optional<ServiceInstance> getServiceInstance(String appName, String instanceId);

    // Health Check
    Optional<String> getInstanceStatus(String appName, String instanceId);
    boolean isHealthy(String appName, String instanceId);
}
