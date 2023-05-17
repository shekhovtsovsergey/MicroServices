package com.example.core.controller;

import com.example.core.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;

    @GetMapping("api/v1/picture/{pictureId}")
    public ResponseEntity<byte[]> downloadPicture(@PathVariable("pictureId") long pictureId) {
        return pictureService.getPictureDataById(pictureId)
                .map(pic -> ResponseEntity
                        .ok()
                        .header(HttpHeaders.CONTENT_TYPE, pic.getContentType())
                        .body(pic.getData())
                ).orElse(ResponseEntity
                        .notFound()
                        .build());
    }
}

