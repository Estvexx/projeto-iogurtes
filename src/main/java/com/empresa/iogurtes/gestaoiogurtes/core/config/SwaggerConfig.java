package com.empresa.iogurtes.gestaoiogurtes.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI gestaoIogurtesOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Gestão de Iogurtes API")
                        .description("Documentação da API para Gestão de Iogurtes")
                        .version("v0.0.1"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("gestao-iogurtes-api")
                .packagesToScan("com.empresa.iogurtes.gestaoiogurtes.core.controller")
                .build();
    }
}
