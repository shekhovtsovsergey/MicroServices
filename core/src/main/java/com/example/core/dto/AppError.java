package com.example.core.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class AppError {
    private String code;//передать код текстовый ORDER_DRAFT_NOT_FOUND
    private String message;
    private Date timestamp;
}

//кастомный конструктор на код и мессадж


