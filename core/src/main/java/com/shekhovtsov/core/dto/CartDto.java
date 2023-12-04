package com.shekhovtsov.core.dto;

import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class CartDto {
    private List<CartItemDto> items;
    private BigDecimal totalPrice;

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);
    }

}
