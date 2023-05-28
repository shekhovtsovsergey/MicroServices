package com.example.storageapp.service;

import com.example.storageapp.dto.PictureDto;
import com.example.storageapp.exception.PictureCreationException;
import com.example.storageapp.exception.PictureNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(
        classes = {PictureServiceImpl.class},
        properties = {"spring.cloud.config.enabled=false"}
)
@DisplayName("Сервис картинок должен")
public class PictureServiceImplTest {

    @Autowired
    PictureServiceImpl pictureService;

    @Test
    @DisplayName("возвращать данные картинки по идентификатору")
    void shouldReturnPictureDataById() throws PictureNotFoundException, IOException {
        String id = "test_id";
        byte[] data = new byte[]{1, 2, 3};
        Path tempDir = Files.createTempDirectory("test");
        File pictureFile = new File(tempDir.toFile(), id);
        pictureFile.createNewFile();
        Files.write(pictureFile.toPath(), data);
        PictureDto pictureDto = new PictureDto(null, pictureFile.toPath(), data);
        PictureService pictureService = new PictureServiceImpl(tempDir.toString());
        Optional<PictureDto> result = pictureService.getPictureDataById(id);
        assertTrue(result.isPresent());
        assertEquals(pictureDto, result.get());
        pictureFile.delete();
        tempDir.toFile().delete();
    }

    @Test
    @DisplayName("создавать картинку")
    void shouldCreatePicture() throws PictureCreationException, IOException {
        byte[] data = new byte[]{1, 2, 3};
        MultipartFile image = mock(MultipartFile.class);
        when(image.getBytes()).thenReturn(data);
        Path tempDir = Files.createTempDirectory("test");
        File pictureFile = new File(tempDir.toFile(), "817d190d-41c6-41fb-90e5-97ab8c33ab58");
        pictureFile.createNewFile();
        PictureService pictureService = new PictureServiceImpl(tempDir.toString());
        String result = pictureService.createPicture(image);
        assertNotNull(result);
        verify(image).getBytes();
        File createdFile = new File(tempDir.toString(), result);
        assertTrue(createdFile.exists());
        pictureFile.delete();
        createdFile.delete();
        tempDir.toFile().delete();
    }


    @Test
    @DisplayName("бросать исключение, если не удалось создать файл")
    void shouldThrowPictureCreationExceptionIfCannotWriteFile() throws IOException {
        MultipartFile image = mock(MultipartFile.class);
        OutputStream os = mock(OutputStream.class);
        when(image.getBytes()).thenReturn(new byte[]{1, 2, 3});
        when(Files.newOutputStream(any())).thenReturn(os);
        doThrow(IOException.class).when(os).write(any(byte[].class));
        PictureService pictureService = new PictureServiceImpl("test_path");
        assertThrows(PictureCreationException.class, () -> pictureService.createPicture(image));
    }

    @Test
    @DisplayName("бросать исключение, если не удалось прочитать файл")
    void shouldThrowPictureNotFoundExceptionIfCannotReadFile() throws IOException {
        String id = "test_id";
        Path picturePath = Paths.get(id).toAbsolutePath();
        when(Files.exists(picturePath)).thenReturn(true);
        when(Files.readAllBytes(picturePath)).thenThrow(IOException.class);
        PictureService pictureService = new PictureServiceImpl("test_path");
        assertThrows(PictureNotFoundException.class, () -> pictureService.getPictureDataById(id));
    }
}
