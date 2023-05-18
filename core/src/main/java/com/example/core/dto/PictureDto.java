package com.example.core.dto;

import lombok.*;

import java.nio.file.Path;


@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PictureDto {

    private String contentType;
    private Path path;
    private byte[] data;

}
