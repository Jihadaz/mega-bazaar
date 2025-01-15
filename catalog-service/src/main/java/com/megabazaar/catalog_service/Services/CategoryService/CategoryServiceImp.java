package com.megabazaar.catalog_service.Services.CategoryService;

import com.megabazaar.catalog_service.Entities.Category;
import com.megabazaar.catalog_service.Entities.Product;
import com.megabazaar.catalog_service.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImp implements ICategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> all() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        if(categoryRepository.existsById(id)){
            return categoryRepository.save(category);
        }
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        if(categoryRepository.existsById(id)){
            Category category=categoryRepository.findById(id).get();
            categoryRepository.delete(category);
            return true;
        }
        return false;
    }
}
