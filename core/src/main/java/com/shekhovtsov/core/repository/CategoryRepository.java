package com.shekhovtsov.core.repository;

import com.shekhovtsov.core.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByTitle(String title);
}
