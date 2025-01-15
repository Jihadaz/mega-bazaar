package com.megabazaar.catalog_service.Mappers;

import com.megabazaar.catalog_service.Dtos.ProductDto;
import com.megabazaar.catalog_service.Entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE= Mappers.getMapper(ProductMapper.class);
    @Mapping(target = "category", source = "categoryDto")
    @Mapping(target = "isActive", defaultValue = "false")
    Product toEntity(ProductDto productDto);
    @Mapping(target = "categoryDto", source = "category")
    ProductDto toDto(Product product);
    @Mapping(target = "category", source = "categoryDto")
    List<Product> toEntities(List<ProductDto> productDtos);
    @Mapping(target = "categoryDto", source = "category")
    List<ProductDto> toDtos(List<Product> products);
}
