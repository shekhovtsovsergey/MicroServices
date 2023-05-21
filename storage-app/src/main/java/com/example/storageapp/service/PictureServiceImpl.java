package com.example.storageapp.service;

import com.example.storageapp.dto.PictureDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PictureServiceImpl implements PictureService {

//    File storage = new File("var/storage");
    private String contentType;
    private final File storage;

    public PictureServiceImpl(@Value("${APP_STORAGE_PATH}") String storagePath){
        this.storage = new File(storagePath);
    }

//    @Override
//    public Optional<PictureDto> getPictureDataById(String id) {
//        System.out.println(storage);
//        System.out.println("Contents of storage directory:");
//        Arrays.stream(storage.listFiles()).forEach(file -> System.out.println(file.getName()));
//
//        String fileName = id;
//        File pictureFile = new File(storage.getPath() + File.separator + fileName);
//        return Optional.of(new PictureDto(contentType, pictureFile.toPath()))
//                .filter(pic -> pic.getPath() != null && pic.getPath().toFile().exists())
//                .map(pic -> {
//                    try {
//                        pic.setData(Files.readAllBytes(pic.getPath()));
//                        return pic;
//                    } catch (IOException ex) {
//                        log.error("Can't read file", ex);
//                        throw new RuntimeException(ex);
//                    }
//                });
//    }



    @Override
    public Optional<PictureDto> getPictureDataById(String id) {
        System.out.println("Contents of storage directory:");
        Arrays.stream(storage.listFiles()).forEach(file -> System.out.println(file.getName()));

        File pictureFile = new File(storage.getPath(), id);
        System.out.println("File pictureFile: " + pictureFile);
        if (pictureFile.exists()) {
            PictureDto pictureDto = new PictureDto(contentType, pictureFile.toPath());
            System.out.println("PictureDto pictureDto: " + pictureDto);
            try {
                pictureDto.setData(Files.readAllBytes(pictureFile.toPath()));
                System.out.println("Returning non-null PictureDto");
                return Optional.of(pictureDto);
            } catch (IOException ex) {
                log.error("Can't read file", ex);
                throw new RuntimeException(ex);
            }
        } else {
            return Optional.empty();
        }
    }


    @Override
    public String createPicture(MultipartFile image) {
        String filename = UUID.randomUUID().toString();
        try (OutputStream os = Files.newOutputStream(Paths.get(storage.getPath(), filename))) {
            os.write(image.getBytes());
        } catch (IOException ex) {
            log.error("Can't write to file", ex);
            throw new RuntimeException(ex);
        }
        return filename;
    }
}


//    @Override
//    public Optional<PictureDto> getPictureDataById(String id) {
//        System.out.println(storage);
//        String fileName = id;
//        Path picturePath = storage.resolve(fileName);
//        return Optional.of(new PictureDto(contentType, picturePath))
//                .filter(pic -> pic.getPath() != null && Files.exists(pic.getPath()))
//                .map(pic -> {
//                    try {
//                        pic.setData(Files.readAllBytes(pic.getPath()));
//                        return pic;
//                    } catch (IOException ex) {
//                        log.error("Can't read file", ex);
//                        throw new RuntimeException(ex);
//                    }
//                });
//    }

//    @Override
//    public Optional<PictureDto> getPictureDataById(String id) {
//        String fileName = id;
//        File pictureFile = new File(storage.getPath() + File.separator + fileName);
//        return Optional.of(new PictureDto(contentType, pictureFile.toPath()))
//                .filter(pic -> pic.getPath() != null && pic.getPath().toFile().exists())
//                .map(pic -> {
//                    try {
//                        pic.setData(Files.readAllBytes(pic.getPath()));
//                        return pic;
//                    } catch (IOException ex) {
//                        log.error("Can't read file", ex);
//                        throw new RuntimeException(ex);
//                    }
//                });
//    }


//    @Override
//    public String createPicture(MultipartFile image) {
//        String filename = UUID.randomUUID().toString();
//        try (OutputStream os = Files.newOutputStream(Paths.get("var/storage", filename))) {
//            os.write(image.getBytes());
//        } catch (IOException ex) {
//            log.error("Can't write to file", ex);
//            throw new RuntimeException(ex);
//        }
//        return filename;
//    }
