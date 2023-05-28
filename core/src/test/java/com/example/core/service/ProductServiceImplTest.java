package com.example.core.service;


import com.example.core.converter.ProductConverter;
import com.example.core.dao.ProductDao;
import com.example.core.dto.ProductDto;
import com.example.core.exception.GlobalExceptionHandler;
import com.example.core.model.Category;
import com.example.core.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(
        classes = {ProductServiceImpl.class},
        properties = {"spring.cloud.config.enabled=false"}
)
@DisplayName("Сервис продуктов должен")
public class ProductServiceImplTest {

    @Autowired
    ProductServiceImpl productService;
    @MockBean
    ProductDao productDao;
    @MockBean
    CategoryServiceImpl categoryService;
    @MockBean
    PictureService pictureService;
    @MockBean
    ProductConverter productConverter;

    @Test
    @DisplayName("находить все продукты")
    public void testFindAll() {
        Specification<Product> spec = Specification.where(null);
        Page<Product> page = new PageImpl<>(Collections.singletonList(new Product()));
        when(productDao.findAll(spec, PageRequest.of(0, 5))).thenReturn(page);
        Page<Product> result = productService.findAll(spec, 0);
        verify(productDao).findAll(spec, PageRequest.of(0, 5));
        assertEquals(page, result);
    }


    @Test
    @DisplayName("находить продукты по id")
    public void testFindById() {
        Long id = 1L;
        Product product = new Product();
        ProductDto productDto = new ProductDto();
        when(productDao.findById(id)).thenReturn(Optional.of(product));
        when(productConverter.entityToDto(product)).thenReturn(productDto);
        Optional<ProductDto> result = productService.findById(id);
        verify(productDao).findById(id);
        verify(productConverter).entityToDto(product);
        assertEquals(Optional.of(productDto), result);
    }


    @Test
    @DisplayName("выбрасывать ошибку если не найден по id")
    public void testFindByIdNotFound() {
        Long id = 1L;
        when(productDao.findById(id)).thenReturn(Optional.empty());
        try {
            productService.findById(id);
            fail("Expected GlobalExceptionHandler.ResourceNotFoundException");
        } catch (GlobalExceptionHandler.ResourceNotFoundException e) {
        }
    }


    @Test
    @DisplayName("удалять продукт по id")
    public void testDeleteById() {
        Long id = 1L;
        productService.deleteById(id);
        verify(productDao).deleteById(id);
    }


    @Test
    @DisplayName("создавать продукт по id")
    public void testCreateNewProduct() throws IOException {
        String title = "Test";
        BigDecimal price = new BigDecimal("10");
        String type = "Type";
        MultipartFile image = new MockMultipartFile("test.jpg", new byte[]{});
        Category category = new Category();
        Product product = new Product(title, price, category);
        when(categoryService.findByTitle(type)).thenReturn(Optional.of(category));
        when(productDao.save(product)).thenReturn(product);
        Product result = productService.createNewProduct(title, price, type, image);
        verify(categoryService).findByTitle(type);
        verify(productDao).save(product);
        verify(pictureService).createPicture(image, product);
        assertEquals(product, result);
    }
}
