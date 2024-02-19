package com.example.cart.controller;

import com.example.cart.converter.CartConverter;
import com.example.cart.dto.CartDto;
import com.example.cart.dto.StringResponse;
import com.example.cart.service.CartService;
import com.example.cart.service.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/api/v1/cart/uuid")
    public StringResponse generateUuid() {
        System.out.println("Request got");
        return cartService.generateUuid();
    }

    @PostMapping("/api/v1/cart/{uuid}/add/{id}")
    public void addToCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid, @PathVariable Long id) {
        cartService.add(username, uuid, id);
    }

    @DeleteMapping("/api/v1/cart/{uuid}/clear")
    public void clearCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid) {
        cartService.clear(username, uuid);
    }

    @DeleteMapping("/api/v1/cart/{uuid}/remove/{id}")
    public void removeFromCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid, @PathVariable Long id) {
        cartService.remove(username, uuid, id);
    }

    @GetMapping("/api/v1/cart/{uuid}")
    public CartDto getCurrentCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid) {
        return cartConverter.entityToDto(cartService.getCurrentCart(username, uuid));
    }

}
