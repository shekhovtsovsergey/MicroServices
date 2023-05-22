package com.example.security.service;

import com.example.security.dto.JwtRequestDto;
import com.example.security.dto.RegistrationUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {
    public String generateToken(UserDetails userDetails);
    public ResponseEntity<?> createAuthToken(JwtRequestDto authRequest);
    public ResponseEntity<?> createAuthTokenAndRegisterUser(RegistrationUserDto registrationUserDto);

}
