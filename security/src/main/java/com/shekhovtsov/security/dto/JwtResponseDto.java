package com.shekhovtsov.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtResponseDto {
    private String token;
}
