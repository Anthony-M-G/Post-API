package com.cr.app.postapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class OpenAPIConfig {
    @Bean
    public OpenAPI openApi(){
        return new OpenAPI().info(new Info()
                .title("Comments API REST")
                .version("1.0.0")
                .description("Aplicación para el manejo de comentarios consumiendo una API externa"));
    }
}
