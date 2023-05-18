package com.example.core.service;


import com.example.core.dto.PictureDto;
import com.example.core.model.Picture;
import com.example.core.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface PictureService {


    Optional<PictureDto> getPictureDataById(long id);
    Picture createPicture(MultipartFile image, Product productId);
}
