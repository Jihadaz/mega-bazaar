package com.megabazaar.catalog_service.Dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductsAvailability {
    private List<Long> ids;
    private List<Integer> quantities;
}
