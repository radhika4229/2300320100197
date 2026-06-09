package com.radhika.Vehicle_Maintenance.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class WebClientConfig {

    @Value("${evaluation.base-url}")
    private String baseUrl;

    @Bean
    public WebClient evaluationWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
