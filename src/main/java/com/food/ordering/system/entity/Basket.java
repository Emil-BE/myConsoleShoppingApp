package com.food.ordering.system.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class Basket {


    private Long id;
    private Long clientId;
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


}
