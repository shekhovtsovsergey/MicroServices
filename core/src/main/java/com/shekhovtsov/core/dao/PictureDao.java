package com.shekhovtsov.core.dao;


import com.shekhovtsov.core.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PictureDao extends JpaRepository<Picture, Long> {

    Optional<Picture> findPictureByProductId(long id);
}
