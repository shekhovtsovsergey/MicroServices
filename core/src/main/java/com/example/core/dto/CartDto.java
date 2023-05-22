package com.example.core.dto;

import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@ToString
public class CartDto {
    private List<CartItemDto> items;
    private BigDecimal totalPrice;

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
