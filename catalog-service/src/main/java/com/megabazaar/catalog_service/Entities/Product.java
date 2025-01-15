package com.megabazaar.catalog_service.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "products")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer code;

    @Column(nullable = false)
    private String imgPath;

    @Column(nullable = false)
    private Boolean isActive = true;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt=LocalDateTime.now();

    @UpdateTimestamp
    @Column(updatable = false)
    private LocalDateTime updatedAt;
    public Product(){}
    public Product(String label, String description, Float price, Integer quantity, Integer code, String imgPath, Boolean isActive, Category category) {
        this.label = label;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.code = code;
        this.imgPath = imgPath;
        this.isActive = isActive;
        this.category=category;
    }
}
