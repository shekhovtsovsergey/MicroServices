package com.example.security.controller;


import com.example.security.config.SecurityConfig;
import com.example.security.config.SecurityConfigTest;
import com.example.security.dto.JwtRequestDto;
import com.example.security.dto.JwtResponseDto;
import com.example.security.dto.RegistrationUserDto;
import com.example.security.service.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;




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
