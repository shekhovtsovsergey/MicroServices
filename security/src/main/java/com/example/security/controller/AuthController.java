package com.example.security.controller;

import com.example.security.model.User;
import com.example.security.service.JwtTokenService;
import com.example.security.service.UserServiceImpl;
import com.example.security.service.JwtTokenServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.security.dto.AppErrorDto;
import com.example.security.dto.JwtRequestDto;
import com.example.security.dto.JwtResponseDto;
import com.example.security.dto.RegistrationUserDto;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenService jwtTokenService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDto authRequest) {
        return jwtTokenService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createAuthTokenAndRegisterUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return jwtTokenService.createAuthTokenAndRegisterUser(registrationUserDto);
    }
}