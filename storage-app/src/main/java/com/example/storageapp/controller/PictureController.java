package com.example.storageapp.controller;


import com.example.storageapp.dto.PictureDto;
import com.example.storageapp.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;


//    @GetMapping("api/v1/picture/{pictureId}")
//    public Optional<PictureDto> downloadPicture(@PathVariable("pictureId") String pictureId) {
//        return pictureService.getPictureDataById(pictureId);
//    }


    @GetMapping("api/v1/picture/{pictureId}")
    public Optional<PictureDto> downloadPicture(@PathVariable("pictureId") String pictureId) {
        Optional<PictureDto> pictureDtoOptional = pictureService.getPictureDataById(pictureId);
        if (!pictureDtoOptional.isPresent()) {
            System.out.println("Picture with ID " + pictureId + " not found");
        } else if (pictureDtoOptional.get() == null) {
            System.out.println("Picture with ID " + pictureId + " has null data");
        } else {
            System.out.println("Picture with ID " + pictureId + " successfully retrieved");
        }
        return pictureDtoOptional;
    }

    @PostMapping("api/v1/picture/")
    public String createPicture(@RequestParam(value = "image") MultipartFile image) {
        return pictureService.createPicture(image);
    }
}
