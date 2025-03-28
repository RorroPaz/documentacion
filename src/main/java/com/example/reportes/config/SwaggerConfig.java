package com.example.reportes.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


/**
 * Acceder a la documentación: http://localhost:8080/swagger-ui/index.html 
 * Pendiente : Aprender a cambiar la ruta para acceder a swagger
 */
@OpenAPIDefinition(
    info = @Info(
        title = "API de Gestión de Documentos",
        version = "1.0",
        description = "API para el sistema de reportes"
    )
)
public class SwaggerConfig {
    // Configuración adicional si necesitas personalizar
}

