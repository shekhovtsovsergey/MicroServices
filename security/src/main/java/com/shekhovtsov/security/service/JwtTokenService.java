package com.shekhovtsov.security.service;

import com.shekhovtsov.security.dto.JwtRequestDto;
import com.shekhovtsov.security.dto.RegistrationUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {
    public String generateToken(UserDetails userDetails);
    public ResponseEntity<?> createAuthToken(JwtRequestDto jwtRequestDto);
    public ResponseEntity<?> createAuthTokenAndRegisterUser(RegistrationUserDto registrationUserDto);

}
