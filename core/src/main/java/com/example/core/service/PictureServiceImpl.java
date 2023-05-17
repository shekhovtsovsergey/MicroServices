package com.example.core.service;

import com.example.core.dao.PictureDao;
import com.example.core.dto.PictureDto;
import com.example.core.integration.PictureServiceIntagration;
import com.example.core.model.Picture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PictureServiceImpl implements PictureService {


    private final PictureServiceIntagration pictureServiceIntagration;
    private final PictureDao pictureDao;


    @Override
    public Optional<PictureDto> getPictureDataById(long id) {
        Optional<Picture> picture = pictureDao.findById(id);
        PictureDto pictureDto = pictureServiceIntagration.getPicture(picture.get().getStorageFileName());
        pictureDto.setContentType(picture.get().getContentType());
        return Optional.ofNullable(pictureDto);
    }





    @Override
    public String createPicture(byte[] pictureData) {
        String filename = UUID.randomUUID().toString();
        try (OutputStream os = Files.newOutputStream(Paths.get("", filename))) {
            os.write(pictureData);
        } catch (IOException ex) {
            log.error("Can't write to file", ex);
            throw new RuntimeException(ex);
        }
        return filename;
    }
}
