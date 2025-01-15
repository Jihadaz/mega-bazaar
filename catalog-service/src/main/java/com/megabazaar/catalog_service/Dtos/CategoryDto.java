package com.megabazaar.catalog_service.Dtos;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    @Nullable
    private Long id;
    private String label;

    public CategoryDto(Long id, String label) {
        this.id=id;
        this.label=label;
    }
}
