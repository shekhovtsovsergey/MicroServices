package com.example.order.service;

import com.example.order.dto.OrderDto;
import com.example.order.dto.PaymentDto;
import org.hibernate.criterion.Order;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<String> createOrder(OrderDto orderDto);
}
