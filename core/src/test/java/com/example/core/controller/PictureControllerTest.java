package com.example.core.controller;

import com.example.core.dto.PictureDto;
import com.example.core.model.Picture;
import com.example.core.service.PictureService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@DisplayName("Тест контроллера картинок")
@AutoConfigureMockMvc
@SpringBootTest
class PictureControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PictureService pictureService;


    @Test
    @DisplayName("Картинка не найдена")
    void shouldReturnNotFoundWhenPictureNotFound() throws Exception {
        long pictureId = 1L;

        given(pictureService.getPictureDataById(pictureId)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/picture/{pictureId}", pictureId))
                .andExpect(status().isNotFound());
    }



    @Test
    public void testDownloadPicture() throws Exception {
        // Mock picture data
        byte[] data = "test data".getBytes();
        String contentType = "image/jpeg";
        PictureDto pictureDto = PictureDto.builder()
                .contentType(contentType)
                .path(Paths.get("test-path"))
                .data(data)
                .build();
        when(pictureService.getPictureDataById(1L)).thenReturn(Optional.of(pictureDto));
        // Send GET request
        mockMvc.perform(get("/api/v1/picture/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(MockMvcResultMatchers.content().bytes(data));
    }


}
