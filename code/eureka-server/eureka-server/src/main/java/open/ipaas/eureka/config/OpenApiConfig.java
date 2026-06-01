package open.ipaas.eureka.config;

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
                        .title("Eureka Server API")
                        .version("1.0")
                        .description("Eureka Service Registry and Discovery API")
                        .contact(new Contact()
                                .name("Eureka Server Team")
                                .email("eureka@example.com")));
    }
}
