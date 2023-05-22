package com.example.storageapp.service;


import com.example.storageapp.dto.PictureDto;
import com.example.storageapp.exception.PictureCreationException;
import com.example.storageapp.exception.PictureNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface PictureService {

    Optional<PictureDto> getPictureDataById(String id) throws PictureNotFoundException;
    String createPicture(MultipartFile image) throws PictureCreationException;
}
