package com.example.cart.service;

import com.example.cart.dto.StringResponse;
import com.example.cart.model.Cart;

import java.util.function.Consumer;

public interface CartService {
    StringResponse generateUuid();
    Cart getCurrentCart(String uuid,String username);
    void add(String uuid,String username, Long productId);
    void remove(String uuid,String username, Long productId);
    void clear(String uuid,String username);
    void execute(String uuid, Consumer<Cart> operation);
    void execute(String uuid, String username, Consumer<Cart> operation);
    String getCartUuid(String username, String uuid);
}
