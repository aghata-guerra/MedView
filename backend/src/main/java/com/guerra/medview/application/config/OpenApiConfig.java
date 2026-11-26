package com.guerra.medview.application.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI medViewOpenApi() {
        return new OpenAPI().info(new Info()
                .title("MedView API")
                .description("Clinical triage support for diabetes and hypertension risk groups")
                .version("v1"));
    }
}
