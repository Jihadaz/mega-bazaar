package com.megabazaar.order_service.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ordered_products")
public class OrderedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long productId;
    @Column(nullable = false)
    private Integer quantity;

    private Float price; // Add price field


    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    public OrderedProduct() {
        // Default constructor
    }
    public OrderedProduct(Long productId, Integer quantity,Float price) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.order=order;
        this.price = price;

    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
