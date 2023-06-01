package com.example.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtRequestDto {
    private String username;
    private String password;
}
