package com.megabazaar.catalog_service.Services.Kafka;

import com.megabazaar.catalog_service.Entities.Product;
import com.megabazaar.catalog_service.Repositories.ProductRepository;
import com.megabazaar.catalog_service.Services.ProductService.ProductServiceImp;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Service
//public class KafkaConsumerService {
//    @Autowired
//    private ProductServiceImp productService;
//    @Autowired
//    private KafkaProducerService kafkaProducerService;
//    @KafkaListener(topics = "dicrease-stock", groupId = "megabazaar-group")
//    public void listen(String message){
//        System.out.println("Received message : "+message);
//        String[] barSplittings=message.split("\\|");
//        List<Pair<Long, Integer>> orderedProducts=new ArrayList<>();
//        String information="";
//        for (String barSplitting:barSplittings){
//            String[] finalSplitting=barSplitting.split(" ");
//            orderedProducts.add(new Pair<>(Long.parseLong(finalSplitting[0]),Integer.parseInt(finalSplitting[1])));
//            information=finalSplitting[2]+" "+finalSplitting[3];
//        }
//        Float totalPrice=productService.dicrease(orderedProducts);
//        String messageToSend=totalPrice+" "+information;
//        kafkaProducerService.sendMessage("invoice-generation", messageToSend);
//    }
//}

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private ProductRepository productRepository;

    @KafkaListener(topics = "dicrease-stock", groupId = "catalog-group")
    public void consumeMessage(String message) {
        String[] productUpdates = message.split(",");
        for (String productUpdate : productUpdates) {
            String[] parts = productUpdate.split(":");
            Long productId = Long.parseLong(parts[0]);
            Integer quantity = Integer.parseInt(parts[1]);

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            if (product.getQuantity() < quantity) {
                throw new RuntimeException("Insufficient stock for product ID: " + productId);
            }

            product.setQuantity(product.getQuantity() - quantity);
            productRepository.save(product);

            System.out.println("Updated stock for product ID: " + productId);
        }
    }
}
