package com.rkisuru.ezbuy.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "EZBuy API",
                version = "v1",
                description = "API documentation for EZBuy application"
        )
)
public class OpenapiConfig {
}
