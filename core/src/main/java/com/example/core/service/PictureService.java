package com.example.core.service;


import com.example.core.dto.PictureDto;

import java.util.Optional;

public interface PictureService {


    Optional<PictureDto> getPictureDataById(long id);

    String createPicture(byte[] pictureData);
}
