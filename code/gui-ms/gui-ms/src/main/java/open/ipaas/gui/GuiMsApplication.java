package open.ipaas.gui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GuiMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuiMsApplication.class, args);
    }
}
