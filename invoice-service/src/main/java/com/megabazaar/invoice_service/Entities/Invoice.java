package com.megabazaar.invoice_service.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Float total;
    @Column(nullable = false)
    private LocalDateTime invoicedAt=LocalDateTime.now();
    @Column(nullable = false)
    private Long orderId;

    public Invoice(Float total, Long orderId) {
        this.total = total;
        this.orderId = orderId;
    }

    public Invoice(Long id, Float total) {
        this.id = id;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public Float getTotal() {
        return total;
    }

    public LocalDateTime getInvoicedAt() {
        return invoicedAt;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public void setInvoicedAt(LocalDateTime invoicedAt) {
        this.invoicedAt = invoicedAt;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
