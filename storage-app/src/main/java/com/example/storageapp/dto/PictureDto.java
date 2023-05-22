package com.example.storageapp.dto;

import lombok.*;
import java.nio.file.Path;


@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class PictureDto {

    private String contentType;
    private Path path;
    private byte[] data;

}
