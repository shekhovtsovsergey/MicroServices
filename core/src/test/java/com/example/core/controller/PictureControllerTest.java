package com.example.core.controller;

import com.example.core.dto.PictureDto;
import com.example.core.model.Picture;
import com.example.core.model.Product;
import com.example.core.service.PictureService;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;


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
}
