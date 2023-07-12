package com.shekhovtsov.bonus.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Table("bonus_details")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class BonusDetail {
    @Id
    private Long id;
    @Column("bonus_id")
    private Long bonusId;
    @Column("order_id")
    private Long orderId;
    @Column("amount")
    private BigDecimal amount;

    public BonusDetail(Long id, Long bonusId, Long orderId, BigDecimal amount) {
        this.id = id;
        this.bonusId = bonusId;
        this.orderId = orderId;
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }
}
