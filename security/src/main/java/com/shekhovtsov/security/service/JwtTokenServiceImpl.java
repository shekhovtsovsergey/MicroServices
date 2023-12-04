package com.shekhovtsov.security.service;

import com.shekhovtsov.security.dto.AppErrorDto;
import com.shekhovtsov.security.dto.JwtRequestDto;
import com.shekhovtsov.security.dto.JwtResponseDto;
import com.shekhovtsov.security.dto.RegistrationUserDto;
import com.shekhovtsov.security.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService{


    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.lifetime}")
    private Integer jwtLifetime;
    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


    @Override
    public ResponseEntity<?> createAuthToken(JwtRequestDto jwtRequestDto) {
        System.out.println("сервис createAuthToken"+jwtRequestDto);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequestDto.getUsername(), jwtRequestDto.getPassword()));
            System.out.println("сервис createAuthToken"+jwtRequestDto);
        } catch (BadCredentialsException e) {
            System.out.println("сервис createAuthToken"+e);
            return new ResponseEntity<>(new AppErrorDto(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(jwtRequestDto.getUsername());
        String token = generateToken(userDetails);
        System.out.println(token);
        return ResponseEntity.ok(new JwtResponseDto(token));
    }


    @Override
    public ResponseEntity<?> createAuthTokenAndRegisterUser(RegistrationUserDto registrationUserDto) {

        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppErrorDto(HttpStatus.BAD_REQUEST.value(), "Passwords do not match"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppErrorDto(HttpStatus.BAD_REQUEST.value(), "A user with this username already exists"), HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setEmail(registrationUserDto.getEmail());
        user.setUsername(registrationUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        userService.createUser(user);

        UserDetails userDetails = userService.loadUserByUsername(registrationUserDto.getUsername());
        String token = generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDto(token));
    }
}
