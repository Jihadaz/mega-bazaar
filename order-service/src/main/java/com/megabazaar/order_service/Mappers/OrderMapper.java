package com.megabazaar.order_service.Mappers;

import com.megabazaar.order_service.Dtos.OrderDto;
import com.megabazaar.order_service.Dtos.OrderedProductDto;
import com.megabazaar.order_service.Entities.Order;
import com.megabazaar.order_service.Entities.OrderedProduct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class OrderMapper{
//    public Order toEntity(OrderDto orderDto) {
//
//        List<OrderedProduct> orderedProducts=new ArrayList<>();
//        for (OrderedProductDto orderedProductDto:orderDto.getOrderedProductDtos()){
//            orderedProducts.add(new OrderedProduct(orderedProductDto.getProductId(), orderedProductDto.getQuantity()));
//        }
//        Order order=new Order(orderDto.getOrderedBy(), orderDto.getReceivedBy(), orderDto.getCin(), orderDto.getCity(), orderDto.getAddress(), orderedProducts, orderDto.getCountry(), orderDto.getEmail());
//        return order;
//    }

    public Order toEntity(OrderDto orderDto) {
        if (orderDto == null) {
            throw new IllegalArgumentException("OrderDto cannot be null");
        }

        List<OrderedProduct> orderedProducts = new ArrayList<>();
        if (orderDto.getOrderedProductDtos() != null) {
            for (OrderedProductDto orderedProductDto : orderDto.getOrderedProductDtos()) {
                if (orderedProductDto != null) {
                    orderedProducts.add(new OrderedProduct(
                            orderedProductDto.getProductId(),
                            orderedProductDto.getQuantity(),
                            orderedProductDto.getPrice() // Map price here

                    ));
                }
            }
        }

        return new Order(
                orderDto.getOrderedBy(),
                orderDto.getReceivedBy(),
                orderDto.getCin(),
                orderDto.getCity(),
                orderDto.getAddress(),
                orderedProducts,
                orderDto.getCountry(),
                orderDto.getEmail()
        );
    }
}
