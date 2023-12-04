package com.shekhovtsov.core.converter;

import com.shekhovtsov.core.dto.ProductDto;
import com.shekhovtsov.core.exception.GlobalExceptionHandler;
import com.shekhovtsov.core.exception.ResourceNotFoundException;
import com.shekhovtsov.core.model.Category;
import com.shekhovtsov.core.model.Product;
import com.shekhovtsov.core.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductConverter {
    private final CategoryService categoryService;

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice(), null);
    }

    public Product dtoToEntity(ProductDto productDto) throws ResourceNotFoundException {
        Product p = new Product();
        p.setId(productDto.getId());
        p.setTitle(productDto.getTitle());
        p.setPrice(productDto.getPrice());
        p.setCategory_id(productDto.getCategoryId());
        return p;
    }
}
