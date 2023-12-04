package com.shekhovtsov.core.integration;

import com.shekhovtsov.core.properties.CartServiceIntegrationProperties;
import com.shekhovtsov.core.properties.PictureServiceIntegrationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegrationsConfig {


    @Bean
    public PictureServiceIntegration pictureServiceIntegration (WebClientFactory webClientFactory, PictureServiceIntegrationProperties properties){
        return new PictureServiceIntegration(webClientFactory.createWebClient(properties.getWebClient()));
    }

    @Bean
    public CartServiceIntegration cartServiceIntegration (WebClientFactory webClientFactory, CartServiceIntegrationProperties properties){
        return new CartServiceIntegration(webClientFactory.createWebClient(properties.getWebClient()));
    }


}
