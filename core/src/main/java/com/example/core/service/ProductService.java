package com.example.core.service;

import com.example.core.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductService {
    Page<Product> findAll(Specification<Product> spec, int page);

    Optional<Product> findById(Long id);

    void deleteById(Long id);

    Product createNewProduct(String title, BigDecimal price, String type, MultipartFile image);

    Specification<Product> createSpecByFilters(Integer minPrice, Integer maxPrice, String title);
}
