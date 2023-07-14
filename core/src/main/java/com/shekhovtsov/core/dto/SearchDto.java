package com.shekhovtsov.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;



@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель поиска")
public class SearchDto {
    @Schema(description = "Минимальная цена", example = "10.00")
    private BigDecimal minPrice= BigDecimal.valueOf(0.00);
    @Schema(description = "Максимальная цена", example = "100.00")
    private BigDecimal maxPrice = BigDecimal.valueOf(1000000.00);
    @Schema(description = "Страница", example = "1")
    private Integer page = 1;
}