package com.megabazaar.catalog_service.Utils.Specifications;

import com.megabazaar.catalog_service.Entities.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


public class ProductSpecification {
    public static Specification<Product> filterByStatusAndCategoryId(Long categoryId){

        return ((root, query, criteriaBuilder) -> {
            Predicate predicate=criteriaBuilder.conjunction();
            predicate=criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("isActive"), true));
            if(categoryId!=0){
                predicate=criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("category").get("id"), categoryId));
            }
            return predicate;
        });
    }
}
