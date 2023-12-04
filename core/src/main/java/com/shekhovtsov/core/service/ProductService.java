package com.shekhovtsov.core.service;

import com.shekhovtsov.core.dto.PageDto;
import com.shekhovtsov.core.dto.ProductDto;
import com.shekhovtsov.core.dto.SearchDto;
import com.shekhovtsov.core.exception.ResourceNotFoundException;
import com.shekhovtsov.core.model.Product;
//import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductService {

    Optional<ProductDto> findById(Long id) throws ResourceNotFoundException;
    void deleteById(Long id);
    Product createNewProduct(ProductDto productDto, MultipartFile image) throws ResourceNotFoundException;
    PageDto<ProductDto> search(SearchDto searchDto);

}
