package com.shekhovtsov.core.integration;

import lombok.Data;
import org.springframework.boot.context.properties.ConstructorBinding;


@ConstructorBinding
@Data
public class BaseServiceIntegrationProperties {
    private String name;
    private String url;
    private Integer connectTimeout;
    private Integer readTimeout;
    private Integer writeTimeout;
}
