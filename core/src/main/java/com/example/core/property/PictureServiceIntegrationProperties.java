package com.example.core.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.storage-app")
@Data
public class PictureServiceIntegrationProperties {
    private String url;
    private Integer connectTimeout;
    private Integer readTimeout;
    private Integer writeTimeout;
}
