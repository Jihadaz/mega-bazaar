package com.megabazaar.catalog_service.ControllerUnitTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

import com.megabazaar.catalog_service.Controllers.ProductController;
import com.megabazaar.catalog_service.Dtos.CategoryDto;
import com.megabazaar.catalog_service.Dtos.ProductDto;
import com.megabazaar.catalog_service.Entities.Category;
import com.megabazaar.catalog_service.Entities.Product;
import com.megabazaar.catalog_service.Mappers.ProductMapper;
import com.megabazaar.catalog_service.Services.ProductService.ProductServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImp productService;

    @MockBean
    private ProductMapper productMapper;

    @Test
    void paginateTest() throws Exception {
        // Arrange
        List<Product> products = Arrays.asList(
                new Product("Label 1", "Description 1", 12.22F, 12, 767678, "image1.jpeg", true, new Category(1L, "Label dto 1")),
                new Product("Label 2", "Description 2", 22.33F, 5, 123456, "image2.jpeg", false, new Category(2L, "Label dto 1"))
        );
        List<ProductDto> productDtos = Arrays.asList(
                new ProductDto("Label 1", "Description 1", 12.22F, 12, 2, "image1.jpeg", true, new CategoryDto(1L, "Label dto 1")),
                new ProductDto("Label 2", "Description 2", 22.33F, 5, 12, "image2.jpeg", true, new CategoryDto(2L, "Label dto 1"))
        );

        Page<Product> pageResult = new PageImpl<>(products, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id")), products.size());

        when(productService.paginate(0L, 0, 10, "id", "desc")).thenReturn(pageResult);
        when(productMapper.toDtos(products)).thenReturn(productDtos);

        // Act and Assert
        mockMvc.perform(get("/api/products")
                        .param("categoryId", "0")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "id")
                        .param("direction", "desc")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products", hasSize(2)))
                .andExpect(jsonPath("$.products[0].label", is("Label 1")))
                .andExpect(jsonPath("$.products[0].price", is(12.22)))
                .andExpect(jsonPath("$.products[1].label", is("Label 2")))
                .andExpect(jsonPath("$.products[1].price", is(22.33)))
                .andExpect(jsonPath("$.currentPage", is(0)))
                .andExpect(jsonPath("$.totalItems", is(2)))
                .andExpect(jsonPath("$.totalPages", is(1)));
    }

    @Test
    void findById() throws Exception {
        Product product=new Product("Label 1", "Description 1", 12.22F, 12, 767678, "image1.jpeg", true, new Category(2L, "Label Category"));
        product.setId(1L);
        ProductDto productDto=new ProductDto("Label 1", "Description 1", 12.22F, 12, 767678, "image1.jpeg", true, new CategoryDto(2L, "Label Category"));
        productDto.setId(1L);
        when(productService.findById(1L)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDto);

        mockMvc.perform(get("/api/products/1/show")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Integer.parseInt(productDto.getId().toString()))))
                .andExpect(jsonPath("$.label", is(productDto.getLabel())))
                .andExpect(jsonPath("$.description", is(productDto.getDescription())))
                .andExpect(jsonPath("$.price", closeTo(productDto.getPrice(), 0.0001)))
                .andExpect(jsonPath("$.quantity", is(productDto.getQuantity())))
                .andExpect(jsonPath("$.imgPath", is(productDto.getImgPath())))
                .andExpect(jsonPath("$.categoryDto.id", is(Integer.parseInt(productDto.getCategoryDto().getId().toString()))))
                .andExpect(jsonPath("$.categoryDto.label", is(productDto.getCategoryDto().getLabel())));
    }
}
