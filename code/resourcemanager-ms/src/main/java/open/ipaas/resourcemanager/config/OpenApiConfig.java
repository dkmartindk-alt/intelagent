package open.ipaas.resourcemanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Resource Manager Microservice API")
                .version("1.0")
                .description("API for managing resources in the iPaas platform")
                .contact(new Contact()
                    .name("Resource Manager Team")
                    .email("resourcemanager@ipaas.com")));
    }
}