package com.example.storageapp.service;


import com.example.storageapp.dto.PictureDto;
import java.util.Optional;

public interface PictureService {


    Optional<PictureDto> getPictureDataById(String id);

    String createPicture(byte[] pictureData);
}
