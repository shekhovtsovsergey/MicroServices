package com.example.core.controller;

import com.example.core.converter.ProductConverter;
import com.example.core.dto.ProductDto;
import com.example.core.model.Category;
import com.example.core.model.Product;
import com.example.core.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = ProductController.class,
        properties = {"spring.cloud.config.enabled=false"}
)
@DisplayName("Контроллер продуктов должен")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @MockBean
    private ProductConverter productConverter;

    @Test
    @DisplayName("найти список продуктов")
    public void testFindProducts() throws Exception {

        List<ProductDto> productList = new ArrayList<>();
        productList.add(new ProductDto(1L, "Product A", new BigDecimal("200.00"), "Category A"));
        Page<ProductDto> productPage = new PageImpl<>(productList);
        when(productService.findAll(any(), anyInt())).thenReturn((Page<Product>) productPage.iterator());
        System.out.println(productService.findAll(any(), anyInt()));

        mockMvc.perform(get("/api/v1/products")
                        .param("min_price", "100")
                        .param("max_price", "500")
                        .param("title", "product")
                        .param("p", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.items[0].title").value("Product A"))
                .andExpect(jsonPath("$.items[0].price").value(200))
                .andExpect(jsonPath("$.items[0].category").value("Category A"))
                .andExpect(jsonPath("$.totalPages").value(1));
        verify(productService).findAll(any(Specification.class), eq(1));
    }


    @Test
    @DisplayName("найти продукт по id")
    public void testFindProductById() throws Exception {

        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setTitle("Cabernet Sauvignon");
        productDto.setPrice(new BigDecimal("1200"));
        productDto.setCategoryTitle("Category A");

        when(productService.findById(anyLong())).thenReturn(Optional.of(productDto));

        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Cabernet Sauvignon"))
                .andExpect(jsonPath("$.price").value(1200))
                .andExpect(jsonPath("$.categoryTitle").value("Category A"))
                .andDo(result -> {
                    String content = result.getResponse().getContentAsString();
                    System.out.println("Response Content: " + content);
                });
    }


    @Test
    @DisplayName("удалить продукт")
    public void testDeleteProductById() throws Exception {
        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("создать продукт")
    public void testCreateNewProduct() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test".getBytes());
        mockMvc.perform(multipart("/api/v1/products")
                        .file(image)
                        .param("title", "New Product")
                        .param("price", "300")
                        .param("category", "New Category"))
                .andExpect(status().isCreated());
    }
}