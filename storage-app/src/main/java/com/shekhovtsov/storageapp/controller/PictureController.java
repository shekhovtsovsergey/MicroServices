package com.shekhovtsov.storageapp.controller;


import com.shekhovtsov.storageapp.dto.PictureDto;
import com.shekhovtsov.storageapp.exception.PictureCreationException;
import com.shekhovtsov.storageapp.exception.PictureNotFoundException;
import com.shekhovtsov.storageapp.service.PictureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PictureController {

    private final PictureService pictureService;

    @GetMapping("api/v1/picture/{pictureId}")
    public Optional<PictureDto> downloadPicture(@PathVariable("pictureId") String pictureId) throws PictureNotFoundException {
        return pictureService.getPictureDataById(pictureId);
    }

    @PostMapping("api/v1/picture/")
    public String createPicture(@RequestParam(value = "image") MultipartFile image) throws PictureCreationException {
        return pictureService.createPicture(image);
    }

    @ExceptionHandler({PictureNotFoundException.class,PictureCreationException.class})
    private ResponseEntity<?> handleException(Exception e) {
        log.error("Error from storage:", e);
        if (e instanceof PictureNotFoundException) {
            return ResponseEntity.notFound().build();
        } else if (e instanceof PictureCreationException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.badRequest().build();
    }
}
