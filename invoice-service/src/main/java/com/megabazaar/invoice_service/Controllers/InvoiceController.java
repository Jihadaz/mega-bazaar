package com.megabazaar.invoice_service.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/invoices")
public class InvoiceController {
    @GetMapping
    public ResponseEntity<String> invoiceMessage(){
        return ResponseEntity.ok("This message is from invoice");
    }
}
