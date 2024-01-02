package com.food.ordering.system.entity;

import java.util.Objects;

public class Order {
    private Product product;
    private Integer quantity;

    public Order(Product product, Integer quantity) {

        this.product = product;
        this.quantity = quantity;
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
