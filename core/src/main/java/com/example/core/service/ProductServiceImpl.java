package com.example.core.service;

import com.example.core.converter.ProductConverter;
import com.example.core.dto.ProductDto;
import com.example.core.exception.GlobalExceptionHandler;
import com.example.core.model.Category;
import com.example.core.model.Product;
import com.example.core.dao.ProductDao;
import com.example.core.dao.specifications.ProductsSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductDao productRepository;
    private final CategoryServiceImpl categoryService;
    private final PictureService pictureService;
    private final ProductConverter productConverter;

    @Override
    public Page<Product> findAll(Specification<Product> spec, int page) {
        return productRepository.findAll(spec, PageRequest.of(page, 5));
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        //Optional<Product> product = productRepository.findById(id);
        //return productConverter.entityToDto(product);
        return Optional.ofNullable(productConverter.entityToDto(productRepository.findById(id).orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Product not found id " + id))));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product createNewProduct(String title, BigDecimal price, String type, MultipartFile image) {
        Category category = categoryService.findByTitle(type).orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Category not found"));
        Product product = new Product(title,price,category);
        productRepository.save(product);
        pictureService.createPicture(image,product);
        return product;
    }

    @Override
    public Specification<Product> createSpecByFilters(Integer minPrice, Integer maxPrice, String title) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (title != null) {
            spec = spec.and(ProductsSpecifications.titleLike(title));
        }
        return spec;
    }
}
