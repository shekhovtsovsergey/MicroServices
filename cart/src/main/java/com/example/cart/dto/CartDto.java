package com.example.cart.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class CartDto {

    private List<CartItemDto> items;
    private BigDecimal totalPrice;

}
