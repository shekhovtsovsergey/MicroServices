package com.shekhovtsov.core.service;

import com.shekhovtsov.core.model.Category;

import java.util.Optional;

public interface CategoryService {
    Optional<Category> findByTitle(String title);

    Optional<Category> findById(Long id);
}
