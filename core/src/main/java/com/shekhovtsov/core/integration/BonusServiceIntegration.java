package com.shekhovtsov.core.integration;

import com.shekhovtsov.core.dto.BonusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BonusServiceIntegration {

    private final WebClient webClient;

    public void add(BonusDto bonusDto) {
        webClient.post()
                .uri("/api/v1/bonuses/add")
                .body(Mono.just(bonusDto), BonusDto.class)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
