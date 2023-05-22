package com.example.storageapp.service;

import com.example.storageapp.dto.PictureDto;
import com.example.storageapp.exception.PictureCreationException;
import com.example.storageapp.exception.PictureNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PictureServiceImpl implements PictureService {

    private String contentType;
    private final String storagePath;
    private final File storage;

    public PictureServiceImpl(@Value("${storage.path}") String storagePath) {
        this.storagePath = storagePath;
        this.storage = new File(storagePath);
   }


    @Override
    public Optional<PictureDto> getPictureDataById(String id) throws PictureNotFoundException {
        try {
            File pictureFile = new File(storage.getPath(), id);
            if (pictureFile.exists()) {
                PictureDto pictureDto = new PictureDto(contentType, pictureFile.toPath(),null);
                try {
                    pictureDto.setData(Files.readAllBytes(pictureFile.toPath()));
                    return Optional.of(pictureDto);
                } catch (IOException ex) {
                    log.error("Can't read file", ex);
                    throw new PictureNotFoundException(id);
                }
            } else {
                throw new PictureNotFoundException(id);
            }
        } catch (Exception e) {
            log.error("An error occurred while getting picture data by id: {}", id, e);
            throw new PictureNotFoundException(id);
        }
    }


    @Override
    public String createPicture(MultipartFile image) throws PictureCreationException{
        String filename = UUID.randomUUID().toString();
        try (OutputStream os = Files.newOutputStream(Paths.get(storage.getPath(), filename))) {
            os.write(image.getBytes());
        } catch (IOException ex) {
            log.error("Can't write file", ex);
            throw new PictureCreationException(filename);
        }
        return filename;
    }
}