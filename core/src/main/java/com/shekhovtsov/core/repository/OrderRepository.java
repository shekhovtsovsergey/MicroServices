package com.shekhovtsov.core.repository;

import com.shekhovtsov.core.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUsername(String username);
}
