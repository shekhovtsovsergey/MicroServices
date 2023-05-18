package com.example.storageapp.service;

import com.example.storageapp.dto.PictureDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@Slf4j
public class PictureServiceImpl implements PictureService {

    File storage = new File("storage");
    private String contentType;

    @Override
    public Optional<PictureDto> getPictureDataById(String id) {
        String fileName = id;
        File pictureFile = new File(storage.getPath() + File.separator + fileName);
        return Optional.of(new PictureDto(contentType, pictureFile.toPath()))
                .filter(pic -> pic.getPath().toFile().exists())
                .map(pic -> {
                    try {
                        pic.setData(Files.readAllBytes(pic.getPath()));
                        return pic;
                    } catch (IOException ex) {
                        log.error("Can't read file", ex);
                        throw new RuntimeException(ex);
                    }
                });
    }


    @Override
    public String createPicture(MultipartFile image) {
        String filename = UUID.randomUUID().toString();
        try (OutputStream os = Files.newOutputStream(Paths.get(".", "storage", filename))) {
            os.write(image.getBytes());
        } catch (IOException ex) {
            log.error("Can't write to file", ex);
            throw new RuntimeException(ex);
        }
        return filename;
    }
}
