package com.example.storageapp.service;


import com.example.storageapp.dto.PictureDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface PictureService {


    Optional<PictureDto> getPictureDataById(String id);

    String createPicture(MultipartFile image);
}
