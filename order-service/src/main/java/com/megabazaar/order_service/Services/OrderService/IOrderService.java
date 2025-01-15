package com.megabazaar.order_service.Services.OrderService;

import com.megabazaar.order_service.Entities.Order;

import java.util.List;

public interface IOrderService {
    List<Order> all();
    Boolean save(Order order);
}
