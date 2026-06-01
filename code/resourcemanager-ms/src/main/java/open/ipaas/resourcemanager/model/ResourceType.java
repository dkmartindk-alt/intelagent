package open.ipaas.resourcemanager.model;

public class ResourceType {
    private String id;
    private String name;
    private String serviceEndpoint;
    private String description;

    // Constructors
    public ResourceType() {}

    public ResourceType(String id, String name, String serviceEndpoint, String description) {
        this.id = id;
        this.name = name;
        this.serviceEndpoint = serviceEndpoint;
        this.description = description;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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