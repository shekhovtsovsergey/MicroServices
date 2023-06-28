package com.shekhovtsov.core.service;

import com.shekhovtsov.core.model.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {
    @Transactional
    Order createOrder(String username);
    List<Order> findByUsername(String username);
}
