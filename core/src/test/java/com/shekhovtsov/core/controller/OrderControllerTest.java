package com.shekhovtsov.core.controller;

import com.shekhovtsov.core.converter.OrderConverter;
import com.shekhovtsov.core.dto.OrderDto;
import com.shekhovtsov.core.model.Order;
import com.shekhovtsov.core.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = OrderController.class,
        properties = {"spring.cloud.config.enabled=false"}
)
@DisplayName("Контроллер заказов должен")
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @MockBean
    private OrderConverter orderConverter;
    private final String username = "testUser";


    @Test
    @DisplayName("успешно создать заказ")
    public void testCreateOrderSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("username", username))
                .andExpect(status().isCreated());

        verify(orderService, times(1)).createOrder(eq(username));
        verifyNoMoreInteractions(orderService);
    }

    @Test
    @DisplayName("получить список заказов по пользователю")
    public void testGetUserOrdersSuccess() throws Exception {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderService.findByUsername(eq(username))).thenReturn(orders);

        List<OrderDto> orderDtos = Arrays.asList(new OrderDto(), new OrderDto());
        when(orderConverter.entityToDto(any(Order.class))).thenReturn(orderDtos.get(0), orderDtos.get(1));

        mockMvc.perform(get("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("username", username))
                .andExpect(status().isOk());

        verify(orderService, times(1)).findByUsername(eq(username));
        verify(orderConverter, times(2)).entityToDto(any(Order.class));
        verifyNoMoreInteractions(orderService, orderConverter);
    }

    @Test
    @DisplayName("не найти заказы")
    public void testGetUserOrdersNotFound() throws Exception {
        when(orderService.findByUsername(eq(username))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("username", username))
                .andExpect(status().isOk());

        verify(orderService, times(1)).findByUsername(eq(username));
        verifyNoMoreInteractions(orderService, orderConverter);
    }
}
