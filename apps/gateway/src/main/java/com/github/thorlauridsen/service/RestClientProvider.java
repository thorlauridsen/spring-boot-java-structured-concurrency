package com.github.thorlauridsen.service;

import com.github.thorlauridsen.config.GatewayConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Provider for {@link RestClient} bean.
 * Configures the {@link RestClient} with the base URL retrieved from {@link GatewayConfig}.
 */
@Component
@RequiredArgsConstructor
public class RestClientProvider {

    private final GatewayConfig gatewayConfig;

    /**
     * Bean for providing a configured {@link RestClient}.
     * It sets the base URL based on the target URL from application.yml.
     *
     * @param builder {@link RestClient.Builder} instance.
     * @return Configured {@link RestClient}.
     */
    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder
                .baseUrl(gatewayConfig.getTargetUrl())
                .build();
    }
}
