package com.shekhovtsov.core.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Reference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("pictures")
@Getter
@Setter
public class Picture {

    @Id
    private Long id;

    @Column("content_type")
    private String contentType;

    @Column("storage_file_name")
    private String storageFileName;

    //@Reference(to = Product.class)
    private Product product;

    @PersistenceConstructor
    public Picture(String contentType, String storageFileName, Product product) {
        this.contentType = contentType;
        this.storageFileName = storageFileName;
        this.product = product;
    }
}
