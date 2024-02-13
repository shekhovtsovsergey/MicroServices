package com.example.order.dto;


import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderDto {
    private String debitAccount;
    private Double amount;
}
