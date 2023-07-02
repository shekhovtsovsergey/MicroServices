package com.shekhovtsov.bonus.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;


@Table("bonus")
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
    private LocalDateTime expireDate;

    public Bonus(Long id, Long clientId, BigDecimal amount, LocalDateTime expireDate) {
        this.id = id;
        this.clientId = clientId;
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.expireDate = expireDate;
    }
}