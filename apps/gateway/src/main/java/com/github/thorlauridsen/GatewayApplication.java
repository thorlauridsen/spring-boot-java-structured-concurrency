package com.github.thorlauridsen;

import com.github.thorlauridsen.config.GatewayConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Main entry point for the gateway application.
 * We need to enable configuration properties for {@link GatewayConfig}.
 * This ensures that Spring Boot will bind the configuration
 * properties from application.yml to the {@link GatewayConfig} class.
 */
@SpringBootApplication
@EnableConfigurationProperties(
        GatewayConfig.class
)
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
