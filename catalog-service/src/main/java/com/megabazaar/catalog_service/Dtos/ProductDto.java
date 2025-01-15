package com.megabazaar.catalog_service.Dtos;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDto {
    @Nullable
    private Long id;
    private String label;
    private String description;
    private Float price;
    private Integer quantity;
    private Integer code;
    private String imgPath;
    private Boolean isActive;
    private CategoryDto categoryDto;

    public ProductDto(String label, String description, Float price, Integer quantity, Integer code, String imgPath, Boolean isActive, CategoryDto categoryDto) {
        this.label=label;
        this.description=description;
        this.price=price;
        this.quantity=quantity;
        this.imgPath=imgPath;
        this.isActive=isActive;
        this.categoryDto=categoryDto;
    }
}
