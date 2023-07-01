package com.shekhovtsov.core.service;

import com.shekhovtsov.core.dto.ProductDto;
import com.shekhovtsov.core.model.Product;
import org.springframework.data.domain.Page;
//import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductService {

    Page<Product> findAll(int page);

    Optional<ProductDto> findById(Long id);

    void deleteById(Long id);

    Product createNewProduct(String title, BigDecimal price, String type, MultipartFile image);
}
