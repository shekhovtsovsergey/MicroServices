package com.shekhovtsov.bonus.dto;


import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    private Long timestamp;
    private String message;
    private String code;

    public ErrorDto(String message, String code) {
        this.message = message;
        this.code = code;
        this.timestamp= System.currentTimeMillis();
    }
}
