package com.shekhovtsov.cart.service;


import com.shekhovtsov.cart.dto.StringResponse;
import com.shekhovtsov.cart.integration.ProductServiceIntegration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest(
        classes = {CartServiceImpl.class},
        properties = {"spring.cloud.config.enabled=false"}
)
@DisplayName("Сервис корзины должен")
public class CartServiceImplTest {


    @Autowired
    CartServiceImpl cartService;
    @MockBean
    ProductServiceIntegration productServiceIntegration;
    @MockBean
    RedisTemplate<String, Object> redisTemplate;


//    @Test
//    @DisplayName("добавлять продукты")
//    void testAdd() {
//        String username = "user1";
//        String uuid = "123456";
//        Long productId = 1L;
//        ProductDto productDto = new ProductDto(productId, "Product 1", BigDecimal.valueOf(10.00),"Category");
//        Cart cart = new Cart();
//        cart.add(productDto);
//        RedisTemplate<String, Object> redisTemplate = Mockito.mock(RedisTemplate.class);
//        Mockito.when(redisTemplate.opsForValue().get(Mockito.anyString())).thenReturn(cart);
//        Mockito.doNothing().when(redisTemplate.opsForValue()).set(Mockito.anyString(), Mockito.any());
//        ProductServiceIntegration productServiceIntegration = Mockito.mock(ProductServiceIntegration.class);
//        Mockito.when(productServiceIntegration.getProductById(Mockito.anyLong())).thenReturn(productDto);
//        CartService cartService = new CartServiceImpl(productServiceIntegration, redisTemplate);
//        cartService.add(username, uuid, productId);
//        Mockito.verify(redisTemplate, Mockito.times(1)).opsForValue().set(Mockito.anyString(), Mockito.any());
//    }


    @Test
    @DisplayName("генерировать UUID")
    void testGenerateUuid() {
        CartService cartService = new CartServiceImpl(Mockito.mock(ProductServiceIntegration.class), Mockito.mock(RedisTemplate.class));
        StringResponse uuid = cartService.generateUuid();
        Assertions.assertNotNull(uuid);
        Assertions.assertNotNull(uuid.getValue());
    }


//    @Test
//    @DisplayName("отдавать текущую корзину")
//    void testGetCurrentCart() {
//        String username = "user1";
//        String uuid = "123456";
//        RedisTemplate<String, Object> redisTemplate = Mockito.mock(RedisTemplate.class);
//        Cart cart = new Cart();
//        Mockito.when(redisTemplate.opsForValue().get(Mockito.anyString())).thenReturn(cart);
//        Mockito.when(redisTemplate.hasKey(Mockito.anyString())).thenReturn(true);
//        CartService cartService = new CartServiceImpl(Mockito.mock(ProductServiceIntegration.class), redisTemplate);
//        Cart result = cartService.getCurrentCart(username, uuid);
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(cart, result);
//    }


//    @Test
//    @DisplayName("удалять из корзины")
//    void testRemove() {
//        String username = "user1";
//        String uuid = "123456";
//        Long productId = 1L;
//        ProductDto productDto = new ProductDto(productId, "Product 1", BigDecimal.valueOf(10.00),"Category");
//        Cart cart = new Cart();
//        cart.add(productDto);
//        RedisTemplate<String, Object> redisTemplate = Mockito.mock(RedisTemplate.class);
//        ValueOperations<String, Object> valueOperations = Mockito.mock(ValueOperations.class);
//        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
//        Mockito.when(valueOperations.get(Mockito.anyString())).thenReturn(cart);
//        Mockito.doNothing().when(valueOperations).set(Mockito.anyString(), Mockito.any());
//        ProductServiceIntegration productServiceIntegration = Mockito.mock(ProductServiceIntegration.class);
//        Mockito.when(productServiceIntegration.getProductById(Mockito.anyLong())).thenReturn(productDto);
//        CartService cartService = new CartServiceImpl(productServiceIntegration, redisTemplate);
//        cartService.remove(username, uuid, productId);
//        Mockito.verify(valueOperations, Mockito.times(1)).set(Mockito.anyString(), Mockito.any());
//        Mockito.verify(valueOperations, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(cartService, Mockito.times(1)).getCurrentCart(Mockito.anyString(), Mockito.anyString());
//    }

//    @Test
//    @DisplayName("очищать корзину")
//    void testClear() {
//        String username = "user1";
//        String uuid = "123456";
//        Cart cart = new Cart();
//        RedisTemplate<String, Object> redisTemplate = Mockito.mock(RedisTemplate.class);
//        Mockito.when(redisTemplate.opsForValue().get(Mockito.anyString())).thenReturn(cart);
//        Mockito.doNothing().when(redisTemplate.opsForValue()).set(Mockito.anyString(), Mockito.any());
//        CartService cartService = new CartServiceImpl(Mockito.mock(ProductServiceIntegration.class), redisTemplate);
//        cartService.clear(username, uuid);
//        Mockito.verify(redisTemplate, Mockito.times(1)).opsForValue().set(Mockito.anyString(), Mockito.any());
//    }
//
//    @Test
//    @DisplayName("производить действия над корзиной")
//    void testExecute() {
//        String uuid = "123456";
//        Cart cart = new Cart();
//        RedisTemplate<String, Object> redisTemplate = Mockito.mock(RedisTemplate.class);
//        Mockito.when(redisTemplate.opsForValue().get(Mockito.anyString())).thenReturn(cart);
//        Mockito.doNothing().when(redisTemplate.opsForValue()).set(Mockito.anyString(), Mockito.any());
//        CartService cartService = new CartServiceImpl(Mockito.mock(ProductServiceIntegration.class), redisTemplate);
//        cartService.execute(uuid, Cart::clear);
//        Mockito.verify(redisTemplate, Mockito.times(1)).opsForValue().set(Mockito.anyString(), Mockito.any());
//    }
//
//    @Test
//    @DisplayName("отдавать идентификатор корзины")
//    void testGetCartUuid() {
//        String username = "user1";
//        String uuid = "123456";
//        CartService cartService = new CartServiceImpl(Mockito.mock(ProductServiceIntegration.class), Mockito.mock(RedisTemplate.class));
//        String result1 = cartService.getCartUuid(username, null);
//        String result2 = cartService.getCartUuid(null, uuid);
//        Assertions.assertEquals(username, result1);
//        Assertions.assertEquals(uuid, result2);
//    }
}
