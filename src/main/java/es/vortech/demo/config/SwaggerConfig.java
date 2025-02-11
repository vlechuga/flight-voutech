package es.vortech.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Prueba Técnica")
                        .version("1.0")
                        .description("Sistema de Gestión de Reservas de Vuelos")
                        .termsOfService("http://swagger.io/terms/"));
    }
}