package com.example.core.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@DisplayName("Тест контроллера продуктов")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindProducts() throws Exception {
        mockMvc.perform(get("/api/v1/products")
                        .param("min_price", "100")
                        .param("max_price", "500")
                        .param("title", "product")
                        .param("p", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(1))
                .andExpect(jsonPath("$.items[0].title").value("Product A"))
                .andExpect(jsonPath("$.items[0].price").value(200))
                .andExpect(jsonPath("$.items[0].category").value("Category A"))
                .andExpect(jsonPath("$.totalPages").value(3));
    }


    @Test
    public void testFindProductById() throws Exception {
        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Cabernet Sauvignon"))
                .andExpect(jsonPath("$.price").value(1200))
                .andExpect(jsonPath("$.category").value("Category A"));
    }

    @Test
    public void testDeleteProductById() throws Exception {
        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isOk());
    }


    @Test
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