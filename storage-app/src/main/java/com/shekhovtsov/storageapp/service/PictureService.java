package com.shekhovtsov.storageapp.service;


import com.shekhovtsov.storageapp.dto.PictureDto;
import com.shekhovtsov.storageapp.exception.PictureCreationException;
import com.shekhovtsov.storageapp.exception.PictureNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface PictureService {

    Optional<PictureDto> getPictureDataById(String id) throws PictureNotFoundException;
    String createPicture(MultipartFile image) throws PictureCreationException;
}
