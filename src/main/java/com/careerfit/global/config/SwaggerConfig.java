package com.careerfit.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    private static final String securitySchemeName = "bearerAuth";

    @Bean
    public List<Server> servers(){
        return List.of(
            new Server().url("https://kareer-fit.com").description("Production Server"),
            new Server().url("http://15.164.71.98:8080").description("Development Server"),
            new Server().url("http://localhost:8080").description("Local Development Server")
        );
    }

    @Bean
    public SecurityScheme securityScheme(){
        return new SecurityScheme()
            .name(securitySchemeName)
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT");
    }

    @Bean
    public SecurityRequirement securityRequirement(){
        return new SecurityRequirement().addList(securitySchemeName);
    }

    @Bean
    public ExternalDocumentation externalDocs() {
        return new ExternalDocumentation()
            .description("Kareer-fit API 상세 명세서 (Notion)")
            .url("https://teamsparta.notion.site/3-2442dc3ef51480c6ac8debb705979847?p=25c2dc3ef5148082bf73d155cca709b5&pm=s");
    }

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
            .title("Kareer-fit 서비스 API 명세서")
            .version("v1.0.0")
            .description("Kareer-fit 프로젝트의 API 명세서입니다.");

        Components components = new Components()
            .addSecuritySchemes(securitySchemeName, securityScheme());

        return new OpenAPI()
            .info(info)
            .servers(servers())
            .addSecurityItem(securityRequirement())
            .components(components)
            .externalDocs(externalDocs());
    }
}
