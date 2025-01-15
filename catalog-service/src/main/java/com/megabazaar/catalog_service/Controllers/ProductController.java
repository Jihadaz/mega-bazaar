package com.megabazaar.catalog_service.Controllers;

import com.megabazaar.catalog_service.Dtos.*;
import com.megabazaar.catalog_service.Entities.Category;
import com.megabazaar.catalog_service.Entities.Product;
import com.megabazaar.catalog_service.Mappers.ProductMapper;
import com.megabazaar.catalog_service.Services.CategoryService.CategoryServiceImp;
import com.megabazaar.catalog_service.Services.ProductService.ProductServiceImp;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping(value = "/api/products")
public class ProductController {
    @Autowired
    private ProductServiceImp productService;
    @Autowired
    private CategoryServiceImp categoryService;
    @Autowired(required = true)
    private ProductMapper productMapper;

    private Map<String, String> errorResponse = new HashMap<>();

    @GetMapping
    public ResponseEntity<Map<String, Object>> paginate(@RequestParam(defaultValue = "0") Long categoryId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "desc") @Pattern(regexp = "asc|desc", flags = Pattern.Flag.CASE_INSENSITIVE) String direction){
        Page<Product> pageResult=productService.paginate(categoryId, page, size, sortBy, direction.toString());
        Map<String, Object> productsPage=new HashMap<>();
        List<ProductDto> productDtos=productMapper.toDtos(pageResult.getContent());
        productsPage.put("products", productDtos);
        productsPage.put("currentPage", pageResult.getNumber());
        productsPage.put("totalItems", pageResult.getTotalElements());
        productsPage.put("totalPages", pageResult.getTotalPages());
        return ResponseEntity.ok(productsPage);
    }

    @GetMapping(value = "/{id}/show")
    public ResponseEntity<?> show(@PathVariable @Pattern(regexp = "[0-9]") Long id){
        Product product=productService.findById(id);
        if(product!=null){
            ProductDto productDto=productMapper.toDto(product);
            return ResponseEntity.ok(productDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    @GetMapping(value = "/find/all")
    public ResponseEntity<List<ProductDto>> findAll(@RequestBody FindAllProductsDto findAllProductsDto){
        List<Product> products=productService.findAllByIds(findAllProductsDto.getIds());
        if (products!=null){
            return ResponseEntity.ok(productMapper.toDtos(products));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(@ModelAttribute ProductDto productDto, @RequestPart("file") MultipartFile file) {
        // Validate category
        if (productDto.getCategoryDto() == null || productDto.getCategoryDto().getId() == null) {
            return ResponseEntity.badRequest().body("Category ID is required");
        }

        if (productDto.getIsActive() == null) {
            productDto.setIsActive(true);
        }

        // Check if category exists
        Category category = categoryService.findById(productDto.getCategoryDto().getId());
        if (category == null) {
            return ResponseEntity.badRequest().body("Invalid Category ID");
        }

        // Map to Product entity and save
        Product product = productMapper.toEntity(productDto);
        product.setCategory(category); // Explicitly set the validated category
        product = productService.save(product, file);

        if (product != null) {
            return ResponseEntity.created(URI.create("/api/products/" + product.getId() + "/show")).build();
        }
        return ResponseEntity.unprocessableEntity().build();
    }


    @PutMapping(value = "/{id}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@PathVariable Long id, @ModelAttribute ProductDto productDto, @RequestPart("file") MultipartFile file){
        Product product=productMapper.toEntity(productDto);
        product=productService.update(id, product, file);
        if(product!=null){
            String location = "/api/products/"+product.getId()+"/show";
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.LOCATION, location);
            return ResponseEntity.noContent().headers(headers).build();
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        Boolean isDeleted=productService.delete(id);
        if(isDeleted){
            return ResponseEntity.ok(isDeleted);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/availability")
    public ResponseEntity<Map<String, List<Boolean>>> productsAvailability(@RequestBody ProductsAvailability productsAvailability){
        List<Boolean> availability=productService.areProductsAvailable(productsAvailability.getIds(), productsAvailability.getQuantities());
        if(availability.size()==productsAvailability.getIds().size() && availability.size()==productsAvailability.getQuantities().size()){
            Map<String, List<Boolean>> response=new HashMap<>();
            response.put("availability", availability);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/details")
    public ResponseEntity<List<ProductDto>> productsDetails(@RequestBody ProductsDetails productsDetails){
        List<Long> ids=productsDetails.getIds();
        List<Product> products=new ArrayList<Product>();
        if (!ids.isEmpty()){
            products=productService.productsDetails(ids);
        }
        if (products.size()!=0 && products.size()==products.size()){
            List<ProductDto> productDtos=productMapper.toDtos(products);
            return ResponseEntity.ok(productDtos);
        }
        return ResponseEntity.notFound().build();
    }


}
