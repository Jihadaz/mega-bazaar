package com.megabazaar.order_service.Dtos;

import jakarta.annotation.Nullable;

public class OrderedProductDto {
    @Nullable
    private Long id;
    private Long productId;
    private Integer  quantity;
    private Float price; // Add price field


    public OrderedProductDto(@Nullable Long id, Long productId, Integer quantity, Float price) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;

    }

    public OrderedProductDto() {
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
