package com.food.ordering.system.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class Basket {
    public Basket(List<Order> orderList, BigDecimal totalAmount) {
        this.orderList = orderList;
        this.totalAmount = totalAmount;
    }


    private List<Order> orderList;
    private BigDecimal totalAmount;

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

    @Override
    public String toString() {

        var result = this.orderList
                .stream()
                .map(order -> order.getProduct().getName() + " " + order.getQuantity())
                .collect(Collectors.joining("\n"));
        return result.concat("\t\t\t\t\t\t\t\t\tTotal amount:\t" + this.getTotalAmount());

    }
}
