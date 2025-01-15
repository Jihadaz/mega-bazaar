package com.megabazaar.catalog_service.Controllers;

import com.megabazaar.catalog_service.Dtos.CategoryDto;
import com.megabazaar.catalog_service.Dtos.ProductDto;
import com.megabazaar.catalog_service.Dtos.ProductsAvailability;
import com.megabazaar.catalog_service.Entities.Category;
import com.megabazaar.catalog_service.Entities.Product;
import com.megabazaar.catalog_service.Mappers.CategoryMapper;
import com.megabazaar.catalog_service.Mappers.ProductMapper;
import com.megabazaar.catalog_service.Services.CategoryService.CategoryServiceImp;
import com.megabazaar.catalog_service.Services.ProductService.ProductServiceImp;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController {
    @Autowired
    private CategoryServiceImp categoryService;

    @Autowired(required = true)
    private CategoryMapper categoryMapper;

    private Map<String, String> errorResponse = new HashMap<>();

    @GetMapping
    public ResponseEntity<List<CategoryDto>> all(){
        List<CategoryDto> categoryDtos=categoryMapper.toDtos(categoryService.all());
        return ResponseEntity.ok(categoryDtos);
    }

    @GetMapping("/{id}/show")
    public ResponseEntity<?> show(@PathVariable @Pattern(regexp = "[0-9]") Long id){
        Category category=categoryService.findById(id);
        if(category!=null){
            CategoryDto categoryDto=categoryMapper.toDto(category);
            return ResponseEntity.ok(categoryDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> save(@RequestBody CategoryDto categoryDto){
        System.out.println(categoryDto.getLabel());
        Category category=categoryMapper.toEntity(categoryDto);
        category=categoryService.save(category);
        if(category!=null){
            return ResponseEntity.created(URI.create("/api/categories/"+category.getId()+"/show")).build();
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @PutMapping(value = "/{id}/update")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CategoryDto categoryDto){
        Category category=categoryMapper.toEntity(categoryDto);
        category=categoryService.update(id, category);
        if(category!=null){
            String location = "/api/products/"+category.getId()+"/show";
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.LOCATION, location);
            return ResponseEntity.noContent().headers(headers).build();
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Boolean> delete(@PathVariable @Pattern(regexp = "[0-9]") Long id){
        Boolean isDeleted=categoryService.delete(id);
        if(isDeleted){
            return ResponseEntity.ok(isDeleted);
        }
        return ResponseEntity.notFound().build();
    }
//    Utiliser des images officielles ou fiables.
//    Minimiser la taille des images Docker.
//    Utiliser des fichiers .dockerignore.
//    Séparer les étapes de construction et d'exécution (multi-stage builds).
//    Ne pas exécuter les conteneurs avec des privilèges root.
//    Configurer les variables d'environnement avec des fichiers .env.
//    Optimiser les couches d'instruction dans le Dockerfile.
//    Limiter les ports exposés et privilégier les réseaux internes.
//    Documenter les commandes dans le Dockerfile.
//    Nettoyer les fichiers temporaires et les dépendances inutiles.
//    Utiliser des outils de gestion de secrets pour les informations sensibles.
//    Automatiser les tests et l'intégration dans un pipeline CI/CD.
//    Activer les logs centralisés pour les conteneurs.
//    Garder le système hôte et Docker à jour.
//    Utiliser des réseaux Docker personnalisés pour l'isolation.
//    Monitorer les conteneurs en production.
}
