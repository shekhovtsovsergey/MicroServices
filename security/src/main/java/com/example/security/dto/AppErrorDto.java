package com.example.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class AppErrorDto {
    private int statusCode;
    private String message;
}
