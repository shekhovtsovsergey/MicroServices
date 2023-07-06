package com.shekhovtsov.bonus.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Table("bonuses")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Bonus {
    @Id
    private Long id;
    @Column("client_id")
    private Long clientId;
    @Column("amount")
    private BigDecimal amount;
    @Column("expire_date")
    private LocalDate expireDate;

    public Bonus(Long id, Long clientId, BigDecimal amount, LocalDate expireDate) {
        this.id = id;
        this.clientId = clientId;
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.expireDate = expireDate;
    }

    public void spend(BigDecimal spendAmount){
        amount=amount.subtract(spendAmount);
    }

}