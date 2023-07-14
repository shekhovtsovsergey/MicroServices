package com.shekhovtsov.core.repository;

import com.shekhovtsov.core.model.Product;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;


public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("SELECT * FROM products WHERE price BETWEEN :minPrice AND :maxPrice;")
    List<Product> findByPriceBetween(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

}
