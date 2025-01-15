package com.megabazaar.catalog_service.Services.ProductService;

import com.megabazaar.catalog_service.Entities.Product;
import com.megabazaar.catalog_service.Repositories.ProductRepository;
import com.megabazaar.catalog_service.Services.FileStorage;
import com.megabazaar.catalog_service.Utils.Specifications.ProductSpecification;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImp implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FileStorage fileStorage;

    @Override
    public List<Product> all() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> paginate(Long categoryId, int page, int size, String sortBy, String direction) {
        Pageable pageable=PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        return productRepository.findAll(ProductSpecification.filterByStatusAndCategoryId(categoryId), pageable);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> findAllByIds(List<Long> ids) {
        List<Product> products=productRepository.findAllById(ids);
        if(products.size()== ids.size()){
            return products;
        }
        return null;
    }

    @Override
    public Product save(Product product, MultipartFile file) {
        try {
            String fileName=fileStorage.save(file);
            product.setImgPath(fileName);
            return productRepository.save(product);
        } catch (IOException e){
            System.out.println("Error is : "+e.getMessage());
        }
        return null;
    }

    @Override
    public Product update(Long id, Product product, MultipartFile file) {
        try {
            if(productRepository.existsById(id)){
                product.setImgPath(fileStorage.update(product.getImgPath(), file));
                return productRepository.save(product);
            }
        } catch (IOException e){
            System.out.println("Error is : "+e.getMessage());
        }
        return null;
    }

    @Override
    public Float dicrease(List<Pair<Long, Integer>> orderedProducts) {
        List<Long> ids=new ArrayList<>();
        for (Pair<Long,Integer> orderedProduct:orderedProducts){
            ids.add(orderedProduct.a);
        }
        List<Product> products=productRepository.findAllById(ids);
        final Float[] total = {0.0F};
        for (int i = 0; i < products.size(); i++) {
            products.get(i).setQuantity(products.get(i).getQuantity()-orderedProducts.get(i).b);
            total[0] = total[0] +products.get(i).getPrice()*orderedProducts.get(i).b;
        }
        productRepository.saveAll(products);
        return total[0];
    }

    @Override
    public Boolean delete(Long id) {
        if(productRepository.existsById(id)){
            try{
                Product product=productRepository.findById(id).get();
                Boolean isFileDeleted=fileStorage.delete(product.getImgPath());
                if(isFileDeleted){
                    productRepository.deleteById(id);
                    return true;
                } else{
                    return false;
                }
            } catch (IOException e) {
                System.out.println("Error occured : "+e.getMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    public List<Boolean> areProductsAvailable(List<Long> ids, List<Integer> quantities) {
        List<Product> products=productRepository.findAllById(ids);
        System.out.println(products.toString());
        List<Boolean> availability=new ArrayList<>();
        if(products.isEmpty() || products.size()!=quantities.size()){
            return availability;
        }
        for (int i = 0; i < products.size(); i++) {
            if(quantities.get(i)==0 || quantities.get(i)>products.get(i).getQuantity()){
                availability.add(false);
            } else{
                availability.add(true);
            }

        }
        return availability;
    }

    @Override
    public List<Product> productsDetails(List<Long> ids) {
        List<Product> products=productRepository.findAllById(ids);
        if (ids.size()==products.size()){
            return products;
        }
        return new ArrayList<>();
    }
}
