package open.ipaas.eureka.model;

import java.util.Map;

public class ServiceInstance {
    private String instanceId;
    private String serviceName;
    private String host;
    private Integer port;
    private String status; // UP, DOWN, STARTING, OUT_OF_SERVICE
    private String ipAddr;
    private String vipAddress;
    private String secureVipAddress;
    private String healthCheckUrl;
    private String homePageUrl;
    private String statusPageUrl;
    private Map<String, String> metadata;

    // Constructors
    public ServiceInstance() {}

    public ServiceInstance(String instanceId, String serviceName, String host, Integer port,
                          String status, String ipAddr, String vipAddress, String secureVipAddress,
                          String healthCheckUrl, String homePageUrl, String statusPageUrl,
                          Map<String, String> metadata) {
        this.instanceId = instanceId;
        this.serviceName = serviceName;
        this.host = host;
        this.port = port;
        this.status = status;
        this.ipAddr = ipAddr;
        this.vipAddress = vipAddress;
        this.secureVipAddress = secureVipAddress;
        this.healthCheckUrl = healthCheckUrl;
        this.homePageUrl = homePageUrl;
        this.statusPageUrl = statusPageUrl;
        this.metadata = metadata;
    }

    // Getters and Setters
    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getVipAddress() {
        return vipAddress;
    }

    public void setVipAddress(String vipAddress) {
        this.vipAddress = vipAddress;
    }

    public String getSecureVipAddress() {
        return secureVipAddress;
    }

    public void setSecureVipAddress(String secureVipAddress) {
        this.secureVipAddress = secureVipAddress;
    }

    public String getHealthCheckUrl() {
        return healthCheckUrl;
    }

    public void setHealthCheckUrl(String healthCheckUrl) {
        this.healthCheckUrl = healthCheckUrl;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    public String getStatusPageUrl() {
        return statusPageUrl;
    }

    public void setStatusPageUrl(String statusPageUrl) {
        this.statusPageUrl = statusPageUrl;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
