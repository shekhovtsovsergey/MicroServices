package com.example.core.service;

import com.example.core.model.Category;

import java.util.Optional;

public interface CategoryService {
    Optional<Category> findByTitle(String title);

    Optional<Category> findById(int id);
}
