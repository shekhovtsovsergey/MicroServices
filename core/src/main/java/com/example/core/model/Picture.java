package com.example.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "pictures")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 1024, nullable = false)
    private String name;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "storage_file_name", length = 256, nullable = false, unique = true)
    private String storageFileName;

    @ManyToOne(optional = false)
    private Product product;

}
