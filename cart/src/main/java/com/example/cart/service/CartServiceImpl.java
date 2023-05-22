package com.example.cart.service;

import com.example.cart.dto.ProductDto;
import com.example.cart.dto.StringResponse;
import com.example.cart.integration.ProductServiceIntegration;
import com.example.cart.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${cart.cart-prefix}")
    private String cartPrefix;

    @Override
    public void add(String uuid, String username,Long productId) {
        ProductDto productDto = productServiceIntegration.getProductById(productId);
        String targetUuid = getCartUuid(username,uuid);
        execute(targetUuid, cart -> cart.add(productDto));
    }

    @Override
    public StringResponse generateUuid() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @Override
    public Cart getCurrentCart(String uuid,String username) {
        String targetUuid = cartPrefix + uuid;
        targetUuid = getCartUuid(username,uuid);
        if (!redisTemplate.hasKey(targetUuid)) {
            redisTemplate.opsForValue().set(targetUuid, new Cart());
        }
        return (Cart)redisTemplate.opsForValue().get(targetUuid);
    }

    @Override
    public void remove(String uuid,String username, Long productId) {
        String targetUuid = getCartUuid(username,uuid);
        execute(targetUuid, cart -> cart.remove(productId));
    }

    @Override
    public void clear(String uuid,String username) {
        String targetUuid = getCartUuid(username,uuid);
        execute(targetUuid,username, Cart::clear);
    }


    @Override
    public void execute(String uuid, String username, Consumer<Cart> operation) {
        Cart cart = getCurrentCart(uuid,username);
        operation.accept(cart);
        redisTemplate.opsForValue().set(cartPrefix + uuid, cart);
    }

    @Override
    public String getCartUuid(String username, String uuid) {
        if (username != null) {
            return username;
        }
        return uuid;
    }
}
