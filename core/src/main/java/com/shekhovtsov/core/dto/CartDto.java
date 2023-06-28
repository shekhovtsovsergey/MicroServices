package com.shekhovtsov.core.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class CartDto {
    private List<CartItemDto> items;
    private BigDecimal totalPrice;//округление сделать
}
