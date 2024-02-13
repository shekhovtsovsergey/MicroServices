package com.example.order.integration;

import com.example.order.dto.DeliveryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class DeliveryServiceIntegration {

    private final WebClient deliveryServiceWebClient;

    public Long getDelivery() {
        return deliveryServiceWebClient.post()
                .uri("/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DeliveryDto())
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }

}
