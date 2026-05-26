package com.brayan.salesflow.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI salesFlowOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SalesFlow CRM API")
                        .description("API REST para gestión comercial, clientes, contactos, oportunidades de venta, actividades y dashboard CRM.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Brayan Jair Chavez Oscor")
                                .url("https://github.com/Brayan1262")
                                .email("brayanjairchavez981@gmail.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositorio GitHub")
                        .url("https://github.com/Brayan1262/salesflow-crm-api"));
    }
}