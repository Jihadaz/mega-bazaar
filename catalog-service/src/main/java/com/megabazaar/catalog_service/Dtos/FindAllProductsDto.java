package com.megabazaar.catalog_service.Dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindAllProductsDto {
    private List<Long> ids;
}
