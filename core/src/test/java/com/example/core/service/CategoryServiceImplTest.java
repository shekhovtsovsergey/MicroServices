package com.example.core.service;

import com.example.core.converter.ProductConverter;
import com.example.core.dao.CategoryDao;
import com.example.core.dao.ProductDao;
import com.example.core.integration.PictureServiceIntagration;
import com.example.core.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(
        classes = {CategoryServiceImpl.class},
        properties = {"spring.cloud.config.enabled=false"}
)
@DisplayName("Сервис категорий должен")
public class CategoryServiceImplTest {

    @Autowired
    CategoryServiceImpl categoryService;
    @MockBean
    ProductDao productDao;
    @MockBean
    CategoryDao categoryDao;
    @MockBean
    ProductConverter productConverter;
    @MockBean
    PictureServiceIntagration pictureServiceIntagration;

    @Test
    @DisplayName("находить категорию по названию")
    public void shouldFindByTitle() {
        String title = "TestCategory";
        Category category = new Category();
        category.setTitle(title);
        CategoryDao categoryDao = mock(CategoryDao.class);
        when(categoryDao.findByTitle(title)).thenReturn(Optional.of(category));
        CategoryService categoryService = new CategoryServiceImpl(categoryDao);
        Optional<Category> result = categoryService.findByTitle(title);
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo(title);
    }

    @Test
    @DisplayName("находить категорию по идентификатору")
    public void shouldFindById() {
        int id = 1;
        Category category = new Category();
        category.setId((long) id);
        CategoryDao categoryDao = mock(CategoryDao.class);
        when(categoryDao.findById(id)).thenReturn(Optional.of(category));
        CategoryService categoryService = new CategoryServiceImpl(categoryDao);
        Optional<Category> result = categoryService.findById(id);
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(id);
    }
}
