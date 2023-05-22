package com.example.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtResponseDto {
    private String token;
}
