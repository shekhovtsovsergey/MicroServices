package com.shekhovtsov.core.service;

import com.shekhovtsov.core.converter.ProductConverter;
import com.shekhovtsov.core.dto.ProductDto;
import com.shekhovtsov.core.exception.GlobalExceptionHandler;
import com.shekhovtsov.core.model.Category;
import com.shekhovtsov.core.model.Product;
import com.shekhovtsov.core.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryServiceImpl categoryService;
    private final PictureService pictureService;
    private final ProductConverter productConverter;


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }


    @Override
    public Optional<ProductDto> findById(Long id) {
        return Optional.ofNullable(productConverter.entityToDto(productRepository.findById(id).orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Product not found id " + id))));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override//добавить валидацию на создание продуктов
    public Product createNewProduct(String title, BigDecimal price, String type, MultipartFile image) {
        Category category = categoryService.findByTitle(type).orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Category not found"));
        Product product = new Product(title,price,category.getId());
        productRepository.save(product);
        pictureService.createPicture(image,product);
        return product;
    }
}
