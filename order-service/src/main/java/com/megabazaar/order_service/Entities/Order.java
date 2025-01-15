package com.megabazaar.order_service.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String orderedBy;
    @Column(nullable = false)
    private String receivedBy;
    @Column(nullable = false)
    private String cin;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String email;
    @OneToMany(mappedBy = "order", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<OrderedProduct> orderedProducts;

    public Order(String orderedBy, String receivedBy, String cin, String city, String address, List<OrderedProduct> orderedProducts, String country, String email) {
        this.orderedBy = orderedBy;
        this.receivedBy = receivedBy;
        this.cin = cin;
        this.city = city;
        this.address = address;
        this.orderedProducts = orderedProducts;
        this.country = country;
        this.email=email;
    }
    public Order() {
        // Default constructor
    }

    public Long getId() {
        return id;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public String getCin() {
        return cin;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
