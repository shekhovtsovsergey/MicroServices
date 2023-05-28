package com.example.core.service;

import com.example.core.converter.ProductConverter;
import com.example.core.dao.OrderDao;
import com.example.core.dao.ProductDao;
import com.example.core.dto.CartDto;
import com.example.core.dto.CartItemDto;
import com.example.core.dto.ProductDto;
import com.example.core.integration.CartServiceIntegration;
import com.example.core.model.Order;
import com.example.core.model.OrderItem;
import com.example.core.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest(
        classes = {OrderServiceImpl.class},
        properties = {"spring.cloud.config.enabled=false"}
)
@DisplayName("Сервис заказов должен")
public class OrderServiceImplTest {

    @Autowired
    OrderServiceImpl orderService;
    @MockBean
    ProductDao productDao;
    @MockBean
    CategoryServiceImpl categoryService;
    @MockBean
    PictureService pictureService;
    @MockBean
    ProductConverter productConverter;
    @MockBean
    CartServiceIntegration cartServiceIntegration;
    @MockBean
    ProductServiceImpl productService;
    @MockBean
    OrderDao orderDao;


    @Test
    @DisplayName("создавать заказы")
    public void shouldCreateOrders() {
        String username = "testUser";
        CartDto cartDto = new CartDto();
        List<CartItemDto> items = new ArrayList<>();
        items.add(new CartItemDto(1L, "Product", 1,BigDecimal.valueOf(10.00),BigDecimal.valueOf(10.00)));
        cartDto.setItems(items);
        cartDto.setTotalPrice(BigDecimal.valueOf(20.00));

        given(cartServiceIntegration.getCurrentCart(username)).willReturn(cartDto);
        given(productService.findById(1L)).willReturn(Optional.of(new ProductDto(1L, "Product", BigDecimal.valueOf(10.00),"Category")));
        given(orderDao.save(any(Order.class))).willAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.createOrder(username);

        verify(cartServiceIntegration, times(1)).getCurrentCart(username);
        verify(productService, times(1)).findById(1L);
        verify(orderDao, times(1)).save(any(Order.class));

        assertNotNull(result);
        assertEquals(result.getUsername(), username);
        assertEquals(result.getTotalPrice(), BigDecimal.valueOf(20.00));
        assertEquals(result.getItems().size(), 1);
        assertEquals(result.getItems().get(0).getQuantity(), 1);
        assertEquals(result.getItems().get(0).getPricePerProduct(), BigDecimal.valueOf(10.00));
        assertEquals(result.getItems().get(0).getPrice(), BigDecimal.valueOf(10.00));
    }

    @Test
    @DisplayName("находить заказы по имени пользователя")
    public void shouldFindOrdersByUsername() {
        String username = "testUser";
        Order order1 = new Order();
        order1.setId(1L);
        order1.setUsername(username);
        order1.setTotalPrice(BigDecimal.valueOf(20.00));
        List<OrderItem> items1 = new ArrayList<>();
        items1.add(new OrderItem(1L, new Product(), order1, 2, BigDecimal.valueOf(10.00),BigDecimal.valueOf(20.00), null, null));
        order1.setItems(items1);
        Order order2 = new Order();
        order2.setId(2L);
        order2.setUsername(username);
        order2.setTotalPrice(BigDecimal.valueOf(30.00));
        List<OrderItem> items2 = new ArrayList<>();
        items2.add(new OrderItem(2L, new Product(), order2, 3, BigDecimal.valueOf(10.00),BigDecimal.valueOf(30.00), null, null));
        order2.setItems(items2);

        given(orderDao.findByUsername(username)).willReturn(Arrays.asList(order1,order2));
        List<Order> result = orderService.findByUsername(username);
        verify(orderDao, times(1)).findByUsername(username);

        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getUsername(), username);
        assertEquals(result.get(0).getTotalPrice(), BigDecimal.valueOf(20.00));
        assertEquals(result.get(0).getItems().size(), 1);
        assertEquals(result.get(0).getItems().get(0).getQuantity(), 2);
        assertEquals(result.get(0).getItems().get(0).getPricePerProduct(), BigDecimal.valueOf(10.00));
        assertEquals(result.get(0).getItems().get(0).getPrice(), BigDecimal.valueOf(20.00));
        assertEquals(result.get(1).getUsername(), username);
        assertEquals(result.get(1).getTotalPrice(), BigDecimal.valueOf(30.00));
        assertEquals(result.get(1).getItems().size(), 1);
        assertEquals(result.get(1).getItems().get(0).getQuantity(), 3);
        assertEquals(result.get(1).getItems().get(0).getPricePerProduct(), BigDecimal.valueOf(10.00));
        assertEquals(result.get(1).getItems().get(0).getPrice(), BigDecimal.valueOf(30.00));
    }
}
