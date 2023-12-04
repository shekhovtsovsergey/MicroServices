package com.shekhovtsov.security.controller;


import com.shekhovtsov.security.dto.JwtResponseDto;
import com.shekhovtsov.security.dto.RegistrationUserDto;
import com.shekhovtsov.security.service.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(
        value = {AuthController.class},
        properties = {"spring.cloud.config.enabled=false"}
)
@DisplayName("Контроллер безопасности должен")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenService jwtTokenService;

/*
    @Test
    @DisplayName("создавать токен и возвращать его")
    void createAuthToken_shouldReturnJwtToken_whenAuthRequestIsValid() throws Exception {
        JwtRequestDto authRequest = new JwtRequestDto("username", "password");
        String token = "token";
        doReturn(ResponseEntity.ok(new JwtResponseDto(token))).when(jwtTokenService).createAuthToken(authRequest);

        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andReturn();
    }*/



    @Test
    @DisplayName("регистрировать пользователя и возвращать токен")
    void createAuthTokenAndRegisterUser_shouldReturnJwtToken_whenRegistrationUserIsValid1() throws Exception {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto("username", "password", "password","email");
        String token = "token";
        doReturn(ResponseEntity.ok(new JwtResponseDto(token))).when(jwtTokenService).createAuthTokenAndRegisterUser(registrationUserDto);

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registrationUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }

    private static String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
