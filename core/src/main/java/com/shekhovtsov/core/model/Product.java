package com.shekhovtsov.core.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Table("products")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Product {

    @Id
    private Long id;
    @NotBlank
    private String title;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Long category_id;
    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;



    public Product(String title, BigDecimal price, Long category) {
        this.title = title;
        this.price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.category_id = category;
    }
}