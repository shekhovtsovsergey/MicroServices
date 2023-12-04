package com.shekhovtsov.core.repository;


import com.shekhovtsov.core.model.Picture;
import org.springframework.data.repository.CrudRepository;


public interface PictureRepository extends CrudRepository<Picture, Long> {

//    Optional<Picture> findPictureByProductId(long id);
}
