package com.example.core.dao;


import com.example.core.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureDao extends JpaRepository<Picture, Long> {
}
