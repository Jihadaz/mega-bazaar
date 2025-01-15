package com.megabazaar.catalog_service.ServiceUnitTests;

import com.megabazaar.catalog_service.Entities.Category;
import com.megabazaar.catalog_service.Repositories.CategoryRepository;
import com.megabazaar.catalog_service.Services.CategoryService.CategoryServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImp categoryService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAll(){
        List<Category> categories= Arrays.asList(
                new Category(1L,"Label 1"),
                new Category(2L,"Label 2")
        );
        when(categoryRepository.findAll()).thenReturn(categories);
        List<Category> result=categoryService.all();
        result.set(3, new Category(3L,"Label 3"));

        assertEquals(2,result.size());
        assertThat(categories.get(0)).isEqualTo(result.get(0));
        assertThat(categories.get(1)).isEqualTo(result.get(1));
        verify(categoryRepository, times(1)).findAll();
    }
}
