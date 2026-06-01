package open.ipaas.eureka.model;

public class RegistrationRequest {
    private ServiceInstance instance;

    public RegistrationRequest() {}

    public RegistrationRequest(ServiceInstance instance) {
        this.instance = instance;
    }

    public ServiceInstance getInstance() {
        return instance;
    }

    public void setInstance(ServiceInstance instance) {
        this.instance = instance;
    }
}
