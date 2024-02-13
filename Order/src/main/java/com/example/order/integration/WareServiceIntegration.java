package com.example.order.integration;

import com.example.order.dto.WareHouseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WareServiceIntegration {

    private final WebClient wareServiceWebClient;

    public ResponseWrapper<Long> getWare() {
        try {
            System.out.println("WareServiceIntegration");
            Long result = wareServiceWebClient.post()
                    .uri("/booking")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new WareHouseDto())
                    .retrieve()
                    .bodyToMono(Long.class)
                    .block();

            return ResponseWrapper.ok(result);

        } catch (WebClientResponseException e) {
            return ResponseWrapper.error();
        }
    }

}
