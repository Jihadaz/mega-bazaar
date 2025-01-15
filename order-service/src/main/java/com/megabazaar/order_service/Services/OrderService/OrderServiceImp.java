package com.megabazaar.order_service.Services.OrderService;

import com.megabazaar.order_service.Entities.Order;
import com.megabazaar.order_service.Entities.OrderedProduct;
import com.megabazaar.order_service.Repository.OrderRepository;
import com.megabazaar.order_service.Repository.OrderedProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImp implements IOrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderedProductRepository orderedProductRepository;
    @Override
    public List<Order> all() {
        return orderRepository.findAll();
    }

    @Override
    public Boolean save(Order order) {
        order=orderRepository.save(order);
        List<OrderedProduct> orderedProducts=new ArrayList<>();
        for (OrderedProduct orderedProduct:order.getOrderedProducts()){
            orderedProduct.setOrder(order);
            orderedProducts.add(orderedProduct);
        }
        orderedProductRepository.saveAll(orderedProducts);
        return true;
    }
}
