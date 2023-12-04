package com.shekhovtsov.cart.service;

import com.shekhovtsov.cart.dto.ProductDto;
import com.shekhovtsov.cart.dto.StringResponse;
import com.shekhovtsov.cart.integration.ProductServiceIntegration;
import com.shekhovtsov.cart.model.Cart;
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
    public void add(String username, String uuid, Long productId) {
        ProductDto productDto = productServiceIntegration.getProductById(productId);
        String targetUuid = getCartUuid(username, uuid);
        execute(targetUuid, cart -> cart.add(productDto));
    }

    @Override
    public StringResponse generateUuid() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @Override
    public Cart getCurrentCart(String username, String uuid) {
        String tmp = getCartUuid(username, uuid);
        String targetUuid = cartPrefix + tmp;
        if (!redisTemplate.hasKey(targetUuid)) {
            redisTemplate.opsForValue().set(targetUuid, new Cart());
        }
        Cart cart = (Cart)redisTemplate.opsForValue().get(targetUuid);
        return cart;
    }

    @Override
    public void remove(String username, String uuid, Long productId) {
        String targetUuid = getCartUuid(username, uuid);
        execute(targetUuid, cart -> cart.remove(productId));
    }

    @Override
    public void clear(String username, String uuid) {
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
