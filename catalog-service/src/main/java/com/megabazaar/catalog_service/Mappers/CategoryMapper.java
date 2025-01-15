package com.megabazaar.catalog_service.Mappers;

import com.megabazaar.catalog_service.Dtos.CategoryDto;
import com.megabazaar.catalog_service.Entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE= Mappers.getMapper(CategoryMapper.class);

    Category toEntity(CategoryDto categoryDto);
    CategoryDto toDto(Category category);

    List<Category> toEntities(List<CategoryDto> categoryDtos);
    List<CategoryDto> toDtos(List<Category> categories);
}
