package com.example.core.dao;


import com.example.core.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PictureDao extends JpaRepository<Picture, Long> {

    Optional<Picture> findPictureByProductId(long id);
}
