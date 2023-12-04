package com.shekhovtsov.core.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        PictureServiceIntegrationProperties.class,
        CartServiceIntegrationProperties.class
})
public class PropertiesConfig {
}
