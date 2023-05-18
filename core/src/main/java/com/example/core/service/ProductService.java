package com.example.core.service;

import com.example.core.dto.ProductDto;
import com.example.core.dto.ResourceNotFoundException;
import com.example.core.model.Category;
import com.example.core.model.Product;
import com.example.core.dao.ProductDao;
import com.example.core.dao.specifications.ProductsSpecifications;
import com.example.core.validator.ProductValidator;
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
public class ProductService {
    private final ProductDao productRepository;
    private final CategoryService categoryService;
    private final ProductValidator productValidator;
    private final PictureService pictureService;


    public Page<Product> findAll(Specification<Product> spec, int page) {
        return productRepository.findAll(spec, PageRequest.of(page, 5));
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }


    public Product createNewProduct(String title, BigDecimal price, String type, MultipartFile image) {
        Category category = categoryService.findByTitle(type).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Product product = new Product(title,price,category);
        productRepository.save(product);
        pictureService.createPicture(image,product);
        return product;
    }


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
