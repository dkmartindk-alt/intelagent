package open.ipaas.resourcemanager.dto;

public class ResourceTypeRequest {
    private String name;
    private String serviceEndpoint;
    private String description;

    // Constructors
    public ResourceTypeRequest() {}

    public ResourceTypeRequest(String name, String serviceEndpoint, String description) {
        this.name = name;
        this.serviceEndpoint = serviceEndpoint;
        this.description = description;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceEndpoint() {
        return serviceEndpoint;
    }

    public void setServiceEndpoint(String serviceEndpoint) {
        this.serviceEndpoint = serviceEndpoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}