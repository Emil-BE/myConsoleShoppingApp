package com.food.ordering.system.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class Basket {


    private Long id;
    private Long clientId;
    private List<Order> orderList;
    private BigDecimal totalAmount;

    private boolean isActive;

    public Basket(Long id, Long clientId,  BigDecimal totalAmount, boolean isActive) {
        this.id = id;
        this.clientId = clientId;
        this.totalAmount = totalAmount;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {

        var result = this.orderList
                .stream()
                .map(order -> order.getProduct().getName() + " " + order.getQuantity())
                .collect(Collectors.joining("\n"));
        return result.concat("\t\t\t\t\t\t\t\t\tTotal amount:\t" + this.getTotalAmount());

    }
}
