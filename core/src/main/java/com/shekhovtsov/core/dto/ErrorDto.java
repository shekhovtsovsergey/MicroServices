package com.shekhovtsov.core.dto;


import lombok.*;


@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    private Long timestamp;
    private String message;
    private int code;

    public ErrorDto(String message, int code) {
        this.message = message;
        this.code = code;
        this.timestamp= System.currentTimeMillis();
    }
}
