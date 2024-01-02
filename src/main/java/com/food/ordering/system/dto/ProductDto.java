package com.food.ordering.system.dto;

import com.food.ordering.system.enums.ProductType;

import java.math.BigDecimal;

public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private ProductType productType;

    public ProductDto(Long id, String name, BigDecimal price, ProductType productType) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productType = productType;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public String toString() {
        return this.id + "\t\t\t" + this.name + "\t\t\t" + this.price + "\t\t\t" + this.productType.name();
    }
}
