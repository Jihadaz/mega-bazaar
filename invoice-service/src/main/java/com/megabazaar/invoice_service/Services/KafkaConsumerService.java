package com.megabazaar.invoice_service.Services;

import com.megabazaar.invoice_service.Entities.Invoice;
import com.megabazaar.invoice_service.Repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
//public class KafkaConsumerService {
//    @Autowired
//    private EmailService emailService;
//    @Autowired
//    private InvoiceRepository invoiceRepository;
//    @KafkaListener(topics = "invoice-generation", groupId = "megabazaar-group")
//    public void listen(String message){
//        System.out.println("Received message : "+message);
//        String[] splitted=message.split(" ");
//        System.out.println(splitted[2]);
//        Invoice invoice=new Invoice(Float.parseFloat(splitted[0]), Long.parseLong(splitted[2]));
//        invoiceRepository.save(invoice);
//        emailService.sendInvoiceEmail(splitted[1],"Megabazaar Order N°"+splitted[2],"You made an order in our store :\n- Order N°"+splitted[2]+"\n- Total paid "+splitted[0]);
//    }
//    @KafkaListener(topics = "invoice-generation", groupId = "megabazaar-group")
//    public void listen(String message){
//        try {
//            System.out.println("Received message : " + message);
//            String[] splitted = message.split(" ");
//            if (splitted.length != 3) {
//                throw new IllegalArgumentException("Invalid message format");
//            }
//
//            float total = Float.parseFloat(splitted[0]);
//            String email = splitted[1];
//            long orderId = Long.parseLong(splitted[2]);
//
//            Invoice invoice = new Invoice(total, orderId);
//            invoiceRepository.save(invoice);
//
//            emailService.sendInvoiceEmail(email, "Megabazaar Order N°" + orderId,
//                    "You made an order in our store:\n- Order N°" + orderId + "\n- Total paid: " + total);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("Failed to process message: " + message);
//        }
//    }


//}


@Service
public class KafkaConsumerService {

//    @KafkaListener(topics = "invoice-generation", groupId = "invoice-service-group")
//    public void listen(String message) {
//        try {
//            System.out.println("Received message : " + message);
//            // Split the message
//            String[] parts = message.split(",");
//            if (parts.length != 3) {
//                throw new IllegalArgumentException("Invalid message format");
//            }
//
//            // Parse each part
//            String orderId = parts[0].split(":")[1];
//            String email = parts[1].split(":")[1];
//            String totalAmount = parts[2].split(":")[1];
//
//            // Process the message (send email, generate invoice, etc.)
//            System.out.printf("Parsed message: orderId=%s, email=%s, totalAmount=%s%n",
//                    orderId, email, totalAmount);
//
//            // Add further processing logic here
//        } catch (Exception e) {
//            System.err.println("Failed to process message: " + message);
//            e.printStackTrace();
//        }
//    }

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "invoice-generation", groupId = "invoice-service-group")
    public void listen(String message) {
        try {
            System.out.println("Received message : " + message);

            // Parse the message
            String[] parts = message.split(",");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid message format");
            }

            String orderId = parts[0].split(":")[1];
            String email = parts[1].split(":")[1];
            String totalAmount = parts[2].split(":")[1];

            // Prepare email details
            String subject = "Invoice for Order #" + orderId;
            String text = String.format(
                    "Dear Customer,\n\nThank you for your purchase. Your total amount is $%.2f.\n\nBest regards,\nMegaBazaar Team",
                    Double.parseDouble(totalAmount)
            );

            // Send the email
            emailService.sendInvoiceEmail(email, subject, text);
        } catch (Exception e) {
            System.err.println("Failed to process message: " + message);
            e.printStackTrace();
        }
    }
}

