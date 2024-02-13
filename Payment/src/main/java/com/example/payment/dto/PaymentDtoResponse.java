package com.example.payment.dto;

import lombok.*;


@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class PaymentDtoResponse {
    private Long id;
    private String message;
}