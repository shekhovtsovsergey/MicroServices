package com.shekhovtsov.bonus.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;


@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель бонуса")
public class BonusDto {
    @Schema(description = "ID бонуса", required = true, example = "1")
    private Long id;
    @Schema(description = "ID клиента", required = true, example = "1")
    private Long clientId;
    @Schema(description = "Сумма бонуса", required = true, example = "50.00")
    private BigDecimal amount;
    @Schema(description = "Дата истечения бонуса", required = true, example = "2022-12-31")
    private LocalDate expireDate;
    @Schema(description = "Категория товара", required = true, example = "1")
    private int category;
    @Schema(description = "ID заказа", example = "1")
    private Long orderId;


    public void setAmount(BigDecimal amount) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }
}