package com.megabazaar.order_service.Repository;

import com.megabazaar.order_service.Entities.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {
}
