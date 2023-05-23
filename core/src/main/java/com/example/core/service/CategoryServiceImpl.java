package com.example.core.service;

import com.example.core.model.Category;
import com.example.core.dao.CategoryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao;

    @Override
    public Optional<Category> findByTitle(String title) {
        return categoryDao.findByTitle(title);
    }
    @Override
    public Optional<Category> findById(int id) {
        return categoryDao.findById(id);
    }

}
