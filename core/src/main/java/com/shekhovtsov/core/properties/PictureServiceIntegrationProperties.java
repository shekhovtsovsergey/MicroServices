package com.shekhovtsov.core.properties;

import com.shekhovtsov.core.integration.WebClientProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.storage-app")
@Getter
@Setter
public class PictureServiceIntegrationProperties {

    private final WebClientProperties webClient;

}
