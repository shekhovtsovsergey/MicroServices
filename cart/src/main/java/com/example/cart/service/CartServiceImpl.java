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
    public void add(String uuid, String username, Long productId) {
        ProductDto productDto = productServiceIntegration.getProductById(productId);
        String targetUuid = getCartUuid(username, uuid);
        execute(targetUuid, cart -> cart.add(productDto));
    }

    @Override
    public StringResponse generateUuid() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @Override
    public Cart getCurrentCart(String uuid, String username) {
        String targetUuid = getCartUuid(username, uuid);
        System.out.println("логируем 1 "+ targetUuid);
        targetUuid = cartPrefix + targetUuid;
        System.out.println("логируем 2 "+ targetUuid);
        if (!redisTemplate.hasKey(targetUuid)) {
            redisTemplate.opsForValue().set(targetUuid, new Cart());
            System.out.println("логируем 3 "+ targetUuid);
        }
        Cart cart = (Cart)redisTemplate.opsForValue().get(targetUuid);
        System.out.println("логируем 4 "+ targetUuid);
        System.out.println("логируем 5 " + cart);
        return cart;
    }

    @Override
    public void remove(String uuid, String username, Long productId) {
        String targetUuid = getCartUuid(username, uuid);
        execute(targetUuid, cart -> cart.remove(productId));
    }

    @Override
    public void clear(String uuid, String username) {
        String targetUuid = getCartUuid(username, uuid);
        execute(targetUuid, Cart::clear);
    }

    @Override
    public void execute(String uuid, Consumer<Cart> operation) {
        Cart cart = getCurrentCart(uuid, null);
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
