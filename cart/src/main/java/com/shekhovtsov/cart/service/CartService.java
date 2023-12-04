package com.shekhovtsov.cart.service;

import com.shekhovtsov.cart.dto.StringResponse;
import com.shekhovtsov.cart.model.Cart;

import java.util.function.Consumer;

public interface CartService {
    StringResponse generateUuid();
    Cart getCurrentCart(String username, String uuid);
    void add(String username, String uuid, Long productId);
    void remove(String username, String uuid, Long productId);
    void clear(String username, String uuid);
    void execute(String uuid, Consumer<Cart> operation);
    String getCartUuid(String username, String uuid);
}
