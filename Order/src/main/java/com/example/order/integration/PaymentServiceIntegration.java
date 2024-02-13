package com.example.order.integration;

import com.example.order.dto.PaymentDto;
import com.example.order.dto.PaymentDtoRequest;
import com.example.order.dto.PaymentDtoResponse;
import com.example.order.dto.WareHouseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class PaymentServiceIntegration {

    private final WebClient paymentServiceWebClient;

    public PaymentDtoResponse getPayment(PaymentDtoRequest paymentDtoRequest) {
        return paymentServiceWebClient.post()
                .uri("/payment/reserve")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(paymentDtoRequest)
                .retrieve()
                .bodyToMono(PaymentDtoResponse.class)
                .block();
    }

    public String cancelPayment(Long id) {
        return paymentServiceWebClient.post()
                .uri("/payment/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }


    public String commitePayment(Long id) {
        return paymentServiceWebClient.post()
                .uri("/payment/pay")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
