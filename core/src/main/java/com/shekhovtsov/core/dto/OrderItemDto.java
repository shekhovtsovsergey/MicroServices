package com.shekhovtsov.core.dto;

import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderItemDto {
    private Long id;
    private String productTitle;
    private Long orderId;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal price;


    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2, RoundingMode.HALF_UP);
    }

    public void setPricePerProduct(BigDecimal pricePerProduct) {
        this.pricePerProduct = pricePerProduct.setScale(2, RoundingMode.HALF_UP);
    }


}
