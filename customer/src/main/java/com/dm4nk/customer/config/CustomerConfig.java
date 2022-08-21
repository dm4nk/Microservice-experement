package com.dm4nk.customer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class CustomerConfig {

    @Value("${fraud-service-url.check}")
    private String checkUrl;

    @Value("${fraud-service-url.fraud}")
    private String fraudUrl;

    @Bean("restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean("checkUriBuilder")
    public UriComponentsBuilder checkUriBuilder() {
        return UriComponentsBuilder.fromUriString(checkUrl);
    }

    @Bean("fraudUriBuilder")
    public UriComponentsBuilder fraudUriBuilder() {
        return UriComponentsBuilder.fromUriString(fraudUrl);
    }
}
