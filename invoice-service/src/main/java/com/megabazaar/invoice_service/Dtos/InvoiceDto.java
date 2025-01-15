package com.megabazaar.invoice_service.Dtos;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class InvoiceDto {
    private Long id;
    private Float total;
    @Nullable
    private LocalDateTime invoicedAt;
    private String path;
    private Long orderId;

    public InvoiceDto(Long id, Float total, String path, Long orderId) {
        this.id = id;
        this.total = total;
        this.path = path;
        this.orderId = orderId;
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

    public String getPath() {
        return path;
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

    public void setPath(String path) {
        this.path = path;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
