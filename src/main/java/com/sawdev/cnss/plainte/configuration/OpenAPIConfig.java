/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sawdev.cnss.plainte.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Aboubacary
 */
@Configuration
public class OpenAPIConfig {

    @Value("${app.openapi.dev-url}")
    private String devUrl;

    @Value("${app.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {

        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("contact@cnss.bf");
        contact.setName("CNSS");
        contact.setUrl("http://www.cnss.bf");

        License appLicense = new License().name("MIT License").url("http://www.cnss.bf");

        Info info = new Info()
                .title("CNSS-PLAINTE-API REST")
                .version("1.0")
                .contact(contact)
                .description("Swagger UI Integration for CNSS-PLAINTE-API REST.")
                .termsOfService("http://www.cnssbf/terms")
                .license(appLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }

}
