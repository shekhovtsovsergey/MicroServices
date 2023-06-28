package com.shekhovtsov.core.integration;

import com.shekhovtsov.core.dto.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {

    private final WebClientFactory webClientFactory;

    public CartDto getCurrentCart(String username) {
        WebClient cartServiceWebClient = webClientFactory.createWebClient("cart");
        return cartServiceWebClient.get()
                .uri("/api/v1/cart/0")
                .header("username", username)
                .retrieve()
                .bodyToMono(CartDto.class)
                .block();
    }

    public void clear(String username) {
        WebClient cartServiceWebClient = webClientFactory.createWebClient("cart");
        cartServiceWebClient.delete()
                .uri("/api/v1/cart/0/clear")
                .header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
