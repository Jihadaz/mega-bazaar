package com.megabazaar.catalog_service.Services.CategoryService;

import com.megabazaar.catalog_service.Entities.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> all();
    Category findById(Long id);
    Category save(Category category);
    Category update(Long id, Category category);
    Boolean delete(Long id);
}
