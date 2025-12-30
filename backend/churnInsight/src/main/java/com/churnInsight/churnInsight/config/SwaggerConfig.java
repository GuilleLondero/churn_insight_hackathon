package com.churnInsight.churnInsight.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI churnInsightAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ChurnInsight API - Predicción de Cancelación de Clientes")
                        .version("1.0.0")
                        .description("API REST para predecir si un cliente cancelará un servicio basándose en sus características y comportamiento. " +
                                "Desarrollado por el Equipo 43 - Hackathon NoCountry H12-25-L")
                        .contact(new Contact()
                                .name("Equipo 43 - NoCountry")
                                .url("https://github.com/GuilleLondero/churn_insight_hackathon"))//github del proyecto
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        //Para desarrollo local
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor Local - Desarrollo"),
                        //Para producción(despliegue)
                        new Server()
                                .url("https://api-churninsight.ejemplo.com")
                                .description("Servidor de Producción")
                ));
    }
}
