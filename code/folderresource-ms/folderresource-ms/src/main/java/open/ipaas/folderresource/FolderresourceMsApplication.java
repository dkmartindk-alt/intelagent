package open.ipaas.folderresource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FolderresourceMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FolderresourceMsApplication.class, args);
    }
}
