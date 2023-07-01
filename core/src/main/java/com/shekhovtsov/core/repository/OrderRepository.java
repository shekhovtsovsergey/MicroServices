package com.shekhovtsov.core.repository;

import com.shekhovtsov.core.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUsername(String username);
}
