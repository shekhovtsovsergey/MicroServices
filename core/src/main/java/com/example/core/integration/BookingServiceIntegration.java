package com.example.core.integration;

import com.example.core.dto.BookingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class BookingServiceIntegration {
    private final WebClient bookingServiceWebClient;

    BookingDto bookingDto = new BookingDto(100,"40817810000000000001");

    public Boolean createOrder() {
        HttpStatus status = bookingServiceWebClient.post()
                .uri("/order")
                .header("")
                .body(BodyInserters.fromValue(bookingDto))
                .exchange()
                .block()
                .statusCode();

        return status.equals(HttpStatus.OK);
    }
}
