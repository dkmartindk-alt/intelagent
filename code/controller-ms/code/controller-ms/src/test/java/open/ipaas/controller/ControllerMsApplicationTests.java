package open.ipaas.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(
    properties = {
        "springdoc.api-docs.enabled=false",
        "springdoc.swagger-ui.enabled=false",
        "eureka.client.enabled=false",
        "spring.cloud.discovery.enabled=false",
    }
)
class ControllerMsApplicationTests {

    @Test
    void contextLoads() {}
}
