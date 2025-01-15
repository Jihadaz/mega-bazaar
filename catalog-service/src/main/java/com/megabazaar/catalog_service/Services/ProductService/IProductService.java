package com.megabazaar.catalog_service.Services.ProductService;

import com.megabazaar.catalog_service.Entities.Product;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {
    List<Product> all();
    Page<Product> paginate(Long categoryId, int page, int size, String sortBy, String direction);
    Product findById(Long id);
    List<Product> findAllByIds(List<Long> ids);
    Product save(Product product, MultipartFile file);
    Product update(Long id, Product product, MultipartFile file);
    Float dicrease(List<Pair<Long,Integer>> orderedProducts);
    Boolean delete(Long id);
    List<Boolean> areProductsAvailable(List<Long> ids, List<Integer> quantities);
    List<Product> productsDetails(List<Long> ids);
}
