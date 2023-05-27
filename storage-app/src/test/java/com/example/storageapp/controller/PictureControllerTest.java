package com.example.storageapp.controller;

import com.example.storageapp.dto.PictureDto;
import com.example.storageapp.exception.PictureCreationException;
import com.example.storageapp.exception.PictureNotFoundException;
import com.example.storageapp.service.PictureService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(
        value = PictureController.class,
        properties = {"spring.cloud.config.enabled=false"}
)
@DisplayName("Контроллер картинок должен")
public class PictureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PictureService pictureService;

    @Test
    @DisplayName("скачать картинку")
    void testDownloadPicture() throws Exception {
        byte[] imageData = {0,1,2,3};
        PictureDto pictureDto = PictureDto.builder()
                .contentType("image/png")
                .path(Paths.get("test.png"))
                .data(imageData)
                .build();

        when(pictureService.getPictureDataById("1")).thenReturn(Optional.of(pictureDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/picture/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.contentType").value("image/png"))
                .andExpect(jsonPath("$.data").value((Base64.getEncoder().encodeToString(imageData))))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        verify(pictureService, times(1)).getPictureDataById("1");
    }


    @Test
    @DisplayName("не скачать карртинку")
    void testDownloadPictureNotFound() throws Exception {
        when(pictureService.getPictureDataById("1")).thenThrow(new PictureNotFoundException(anyString()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/picture/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(pictureService, times(1)).getPictureDataById("1");
    }

    @Test
    @DisplayName("сохранить картинку")
    void testCreatePicture() throws Exception {
        String pictureId = "1";
        when(pictureService.createPicture(any())).thenReturn(pictureId);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/picture/")
                        .file("image", "test.png".getBytes()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(pictureId));

        ArgumentCaptor<MultipartFile> fileCaptor = ArgumentCaptor.forClass(MultipartFile.class);
        verify(pictureService, times(1)).createPicture(fileCaptor.capture());
        assertEquals("test.png", fileCaptor.getValue().getOriginalFilename());
    }

    @Test
    @DisplayName("не сохранить картинку")
    void testCreatePictureException() throws Exception {
        when(pictureService.createPicture(any())).thenThrow(new PictureCreationException(anyString()));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/picture/")
                        .file("image", "test.png".getBytes()))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

        ArgumentCaptor<MultipartFile> fileCaptor = ArgumentCaptor.forClass(MultipartFile.class);
        verify(pictureService, times(1)).createPicture(fileCaptor.capture());
        assertEquals("test.png", fileCaptor.getValue().getOriginalFilename());
    }

    @Test
    @DisplayName("поймать ошибку")
    void testExceptionHandler() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/picture/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/picture/")
                        .file("image", "test.png".getBytes()))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

    }
}
