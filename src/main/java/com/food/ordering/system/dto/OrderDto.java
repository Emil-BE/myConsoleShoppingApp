package com.food.ordering.system.dto;

import com.food.ordering.system.entity.Product;

public class OrderDto {
    private Long id;
    private Product product;
    private Integer quantity;
    private boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public OrderDto(Long id, Product product, Integer quantity, boolean isActive) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.isActive = isActive;
    }
}
