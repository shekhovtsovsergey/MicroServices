package com.shekhovtsov.core.service;

import com.shekhovtsov.core.dto.ProductDto;
import com.shekhovtsov.core.model.Product;
//import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<ProductDto> findById(Long id);

    void deleteById(Long id);

    Product createNewProduct(String title, BigDecimal price, String type, MultipartFile image);
}
