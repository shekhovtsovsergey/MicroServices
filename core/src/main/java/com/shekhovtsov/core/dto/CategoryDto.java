package com.shekhovtsov.core.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoryDto {
    private Long id;
    private String title;
    private List<ProductDto> products;

}
