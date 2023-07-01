package com.shekhovtsov.core.service;


import com.shekhovtsov.core.dto.PictureDto;
import com.shekhovtsov.core.model.Picture;
import com.shekhovtsov.core.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface PictureService {

    Picture createPicture(MultipartFile image, Product productId);
}
