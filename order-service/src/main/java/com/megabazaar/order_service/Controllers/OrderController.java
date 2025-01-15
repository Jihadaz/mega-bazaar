package com.megabazaar.order_service.Controllers;

import com.megabazaar.order_service.Dtos.OrderDto;
import com.megabazaar.order_service.Dtos.OrderedProductDto;
import com.megabazaar.order_service.Entities.Order;
import com.megabazaar.order_service.Entities.OrderedProduct;
import com.megabazaar.order_service.Mappers.OrderMapper;
import com.megabazaar.order_service.Services.Kafka.KafkaProducerService;
import com.megabazaar.order_service.Services.OrderService.OrderServiceImp;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {
    @Autowired
    private OrderServiceImp orderService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private KafkaProducerService kafkaProducerService;


//    @PostMapping(value = "/save")
//    public ResponseEntity<?> save(@RequestBody OrderDto orderDto) {
//        if (orderDto.getOrderedProductDtos() == null || orderDto.getOrderedProductDtos().isEmpty()) {
//            return ResponseEntity.badRequest().body("Ordered products must not be empty.");
//        }
//        System.out.println("Received orderDto: " + orderDto);
//
//
//        // Validate other fields
//        if (orderDto.getEmail() == null || orderDto.getEmail().isEmpty()) {
//            return ResponseEntity.badRequest().body("Email is required.");
//        }
//        if (orderDto.getCin() == null || orderDto.getCin().isEmpty()) {
//            return ResponseEntity.badRequest().body("CIN is required.");
//        }
//        if (orderDto.getCity() == null || orderDto.getCity().isEmpty()) {
//            return ResponseEntity.badRequest().body("City is required.");
//        }
//        if (orderDto.getAddress() == null || orderDto.getAddress().isEmpty()) {
//            return ResponseEntity.badRequest().body("Address is required.");
//        }
//        if (orderDto.getCountry() == null || orderDto.getCountry().isEmpty()) {
//            return ResponseEntity.badRequest().body("Country is required.");
//        }
//
//        try {
//            Order order = orderMapper.toEntity(orderDto);
//            orderService.save(order);
//            // Publish to Kafka
//            String message = order.getOrderedProducts().stream()
//                    .map(product -> product.getProductId() + ":" + product.getQuantity())
//                    .reduce((a, b) -> a + "," + b)
//                    .orElse("");
//            kafkaProducerService.sendMessage("dicrease-stock", message);
//
//            return ResponseEntity.ok("Order saved and message sent to Kafka.");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error saving order: " + e.getMessage());
//        }
//    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> save(@RequestBody OrderDto orderDto) {
        // Validation logic (omitted for brevity)

        try {
            // Map DTO to Entity
            Order order = orderMapper.toEntity(orderDto);

            // Save the order
            orderService.save(order);

            // Prepare stock update message for Kafka
            String stockMessage = order.getOrderedProducts().stream()
                    .map(product -> product.getProductId() + ":" + product.getQuantity())
                    .reduce((a, b) -> a + "," + b)
                    .orElse("");

            kafkaProducerService.sendMessage("dicrease-stock", stockMessage);

            // Prepare invoice generation message for Kafka
            double totalAmount = order.getOrderedProducts().stream()
                    .mapToDouble(product -> product.getQuantity() * product.getPrice())
                    .sum();

            String invoiceMessage = String.format(
                    "orderId:%d,email:%s,totalAmount:%.2f",
                    order.getId(),
                    order.getEmail(),
                    totalAmount
            );

            kafkaProducerService.sendMessage("invoice-generation", invoiceMessage);

            return ResponseEntity.ok("Order saved. Messages sent to Kafka.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving order: " + e.getMessage());
        }
    }


}
