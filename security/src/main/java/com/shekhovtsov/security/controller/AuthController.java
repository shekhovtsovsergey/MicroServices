package com.shekhovtsov.security.controller;

import com.shekhovtsov.security.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.shekhovtsov.security.dto.JwtRequestDto;
import com.shekhovtsov.security.dto.RegistrationUserDto;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenService jwtTokenService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDto jwtRequestDto) {
        System.out.println("контроллер createAuthToken" + jwtRequestDto);
        ResponseEntity<?> token = jwtTokenService.createAuthToken(jwtRequestDto);
        System.out.println(token);
        return token;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createAuthTokenAndRegisterUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return jwtTokenService.createAuthTokenAndRegisterUser(registrationUserDto);
    }
}