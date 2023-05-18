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


    @GetMapping("api/v1/picture/{pictureId}")
    public Optional<PictureDto> downloadPicture(@PathVariable("pictureId") String pictureId) {
        return pictureService.getPictureDataById(pictureId);
    }


    @PostMapping("api/v1/picture/")
    public String createPicture(@RequestParam(value = "image") MultipartFile image) {
        return pictureService.createPicture(image);
    }
}
