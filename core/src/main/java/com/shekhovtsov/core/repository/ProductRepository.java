package com.shekhovtsov.core.repository;

import com.shekhovtsov.core.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();
}
