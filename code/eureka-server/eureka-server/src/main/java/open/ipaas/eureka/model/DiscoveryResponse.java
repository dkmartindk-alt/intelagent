package open.ipaas.eureka.model;

import java.util.List;

public class DiscoveryResponse {
    private List<Application> applications;
    private String appsHashCode;

    public DiscoveryResponse() {}

    public DiscoveryResponse(List<Application> applications, String appsHashCode) {
        this.applications = applications;
        this.appsHashCode = appsHashCode;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public String getAppsHashCode() {
        return appsHashCode;
    }

    public void setAppsHashCode(String appsHashCode) {
        this.appsHashCode = appsHashCode;
    }
}
