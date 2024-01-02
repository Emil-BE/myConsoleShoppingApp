package com.food.ordering.system.mapper;

import com.food.ordering.system.dto.ProductDto;
import com.food.ordering.system.entity.Product;

public class ProductMapper {

    public static ProductDto productToProductDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getProductType());
    }
}
