package com.example.projet_inaf.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(" Documentation d' APIs")
                        .description("Documentation interactive d'APIs pour mon projet")
                        .version("v1.0.4"));
    }
}
