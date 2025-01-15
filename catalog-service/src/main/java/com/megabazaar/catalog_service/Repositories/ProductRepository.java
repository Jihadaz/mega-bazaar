package com.megabazaar.catalog_service.Repositories;

import com.megabazaar.catalog_service.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findByLabelContainingOrDescriptionContainingIgnoreCase(String labelName, String descriptionName);
    // List<Product> findByIdIn(List<Long> ids);
}
