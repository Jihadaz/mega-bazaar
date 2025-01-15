package com.megabazaar.catalog_service.RepositoryUnitTests;

import com.megabazaar.catalog_service.Entities.Category;
import com.megabazaar.catalog_service.Entities.Product;
import com.megabazaar.catalog_service.Repositories.CategoryRepository;
import com.megabazaar.catalog_service.Repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void emptyResultTest(){
        Iterable<Product> products=productRepository.findAll();
        assertThat(products).isEmpty();
    }

    @Test
    public void findAllProducts(){
        Category category1=new Category(1L, "Label 1");
        testEntityManager.persist(category1);
        Category category2=new Category(2L,"Label 2");
        testEntityManager.persist(category2);
        Category category3=new Category(3L, "Label 3");
        testEntityManager.persist(category3);
        Product product1=new Product("Label 1", "Description 1", 12.22F, 12, 767678, "PQP38493P38EP.jpeg", true, category1);
        product1.setCategory(category1);
        testEntityManager.persist(product1);
        Product product2=new Product("Label 2", "Description 2", 12.22F, 12, 767678, "PQP38493P38EP.jpeg", true, category2);
        product2.setCategory(category2);
        testEntityManager.persist(product2);
        Product product3=new Product("Label 3", "Description 3", 12.22F, 12, 767678, "PQP38493P38EP.jpeg", true, category3);
        product3.setCategory(category3);
        testEntityManager.persist(product3);

        testEntityManager.flush();
        Iterable<Product> products=productRepository.findAll();
        assertThat(products).hasSize(3).contains(product1,product2,product3);
    }

    @Test
    public void findById(){
        Category category=new Category(1L, "Label 1");
        testEntityManager.persist(category);
        Product product=new Product("Label 1", "Description 1", 12.22F, 12, 767678, "PQP38493P38EP.jpeg", true, category);
        product.setCategory(category);
        testEntityManager.persist(product);
        testEntityManager.flush();

        Product productTest=productRepository.findById(product.getId()).get();
        assertThat(productTest).isEqualTo(product);
    }
}
