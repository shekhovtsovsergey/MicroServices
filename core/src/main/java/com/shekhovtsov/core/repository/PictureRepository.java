package com.shekhovtsov.core.repository;


import com.shekhovtsov.core.model.Picture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface PictureRepository extends CrudRepository<Picture, Long> {

//    Optional<Picture> findPictureByProductId(long id);
}
