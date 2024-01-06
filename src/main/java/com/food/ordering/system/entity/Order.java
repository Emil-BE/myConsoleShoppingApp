package com.food.ordering.system.entity;

import java.util.Objects;

public class Order {
    private Long id;
    private Product product;
    private Integer quantity;
    private boolean isActive;

    public Order(Long id, Product product, Integer quantity, boolean isActive) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.isActive = isActive;
    }

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

    @Override
    public String toString() {
        return "Product name:" + this.product.getName() + "\t\t\t" + "Quantity: " + this.quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(product, order.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }
}
