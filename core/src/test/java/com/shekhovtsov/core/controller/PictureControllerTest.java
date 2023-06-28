package com.shekhovtsov.core.controller;

import com.shekhovtsov.core.dto.PictureDto;
import com.shekhovtsov.core.service.PictureService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Paths;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(
        value = PictureController.class,
        properties = {"spring.cloud.config.enabled=false"}
)
@DisplayName("Контроллер картинок должен")
class PictureControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PictureService pictureService;


    @Test
    @DisplayName("не найти картинку")
    void shouldReturnNotFoundWhenPictureNotFound() throws Exception {
        long pictureId = 1L;

        given(pictureService.getPictureDataById(pictureId)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/picture/{pictureId}", pictureId))
                .andExpect(status().isNotFound());
    }



    @Test
    @DisplayName("найти картинку")
    public void testDownloadPicture() throws Exception {
        byte[] data = "test data".getBytes();
        String contentType = "image/jpeg";
        PictureDto pictureDto = PictureDto.builder()
                .contentType(contentType)
                .path(Paths.get("test-path"))
                .data(data)
                .build();
        when(pictureService.getPictureDataById(1L)).thenReturn(Optional.of(pictureDto));
        mockMvc.perform(get("/api/v1/picture/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(MockMvcResultMatchers.content().bytes(data));
    }


}
