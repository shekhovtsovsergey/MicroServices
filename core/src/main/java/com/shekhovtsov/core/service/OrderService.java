package com.shekhovtsov.core.service;

import com.shekhovtsov.core.dto.OrderDto;
import com.shekhovtsov.core.dto.PageDto;
import com.shekhovtsov.core.model.Order;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {
    @Transactional
    Order createOrder(String username);
    PageDto<OrderDto> findByUsername(String username);
}
