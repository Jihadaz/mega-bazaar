package com.megabazaar.catalog_service.RepositoryUnitTests;

import com.megabazaar.catalog_service.Entities.Category;
import com.megabazaar.catalog_service.Repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/*
    SpringExtension is a part of the spring test library
    and it allows Spring-managed beans like repositories
    and services to be injected into test class.
    This enables you to run tests in a spring application
    context, which is required to perform tests on Spring
    components like repositories or services.
 */
@ExtendWith(SpringExtension.class)
/*
    This is a specialized annotation from Spring Boot used
    for testing JPA components, such as repositories.
    It configures the Spring context with only the beans
    related to JPA, such as EntityManager, DataSource,
    and repository beans.
*/
@DataJpaTest
public class CategoryRepositoryTest {
    /*
        TestEntityManager is a helper class provided by
        Spring Data JPA for interacting with the persistence
        context during tests.
    */
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void emptyResultTest(){
        Iterable<Category> categories=categoryRepository.findAll();
        assertThat(categories).isEmpty();
    }

    @Test
    public void findAllCategories(){
        Category category1=new Category(1L, "Label 1");
        testEntityManager.persist(category1);
        Category category2=new Category(2L, "Label 2");
        testEntityManager.persist(category2);
        Category category3=new Category(3L, "Label 3");
        testEntityManager.persist(category3);

        testEntityManager.flush();
        Iterable<Category> categories=categoryRepository.findAll();
        assertThat(categories).hasSize(3).contains(category1,category2,category3);
    }

    @Test
    public void findById(){
        Category category=new Category(1L,"Label by id");
        testEntityManager.persist(category);
        testEntityManager.flush();

        Category categoryTest=categoryRepository.findById(category.getId()).get();
        assertThat(categoryTest).isEqualTo(category);
    }


}
