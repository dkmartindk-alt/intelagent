package open.ipaas.resourceaccesscontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ResourceaccesscontrolMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceaccesscontrolMsApplication.class, args);
    }
}
