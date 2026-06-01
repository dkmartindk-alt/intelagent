package open.ipaas.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ControllerMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControllerMsApplication.class, args);
    }
}
