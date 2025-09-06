package com.github.thorlauridsen.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the gateway subproject.
 * Holds the URL of the target service.
 */
@Data
@ConfigurationProperties(prefix = "gateway.settings")
public class GatewayConfig {

    /**
     * URL of the target service.
     */
    private final String targetUrl;
}
