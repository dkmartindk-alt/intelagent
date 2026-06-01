package open.ipaas.eureka.model;

import java.util.List;

public class Application {
    private String name;
    private List<ServiceInstance> instances;

    public Application() {}

    public Application(String name, List<ServiceInstance> instances) {
        this.name = name;
        this.instances = instances;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ServiceInstance> getInstances() {
        return instances;
    }

    public void setInstances(List<ServiceInstance> instances) {
        this.instances = instances;
    }
}
