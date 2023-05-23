package com.example.core.dto;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class AppError {
    private int statusCode;
    private String message;
}
