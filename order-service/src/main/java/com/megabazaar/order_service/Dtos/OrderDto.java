package com.megabazaar.order_service.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.megabazaar.order_service.Entities.OrderedProduct;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class OrderDto {
    @Nullable
    private Long id;
    private String orderedBy;
    private String receivedBy;
    private String cin;
    private String city;
    private String country;
    private String address;
    private String email;
    @JsonProperty("orderedProducts")
    private List<OrderedProductDto> orderedProductDtos = new ArrayList<>();

    public OrderDto(@Nullable Long id, String orderedBy, String receivedBy, String cin, String city, String country, String address, List<OrderedProductDto> orderedProductDtos, String email) {
        this.id = id;
        this.orderedBy = orderedBy;
        this.receivedBy = receivedBy;
        this.cin = cin;
        this.city = city;
        this.country = country;
        this.address = address;
        this.orderedProductDtos = orderedProductDtos;
        this.email=email;
    }

    @Nullable
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

    public List<OrderedProductDto> getOrderedProductDtos() {
        return orderedProductDtos;
    }

    public void setId(@Nullable Long id) {
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

    public void setOrderedProductDtos(List<OrderedProductDto> orderedProductDtos) {
        this.orderedProductDtos = orderedProductDtos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
