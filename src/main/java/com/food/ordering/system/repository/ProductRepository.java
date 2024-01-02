package com.food.ordering.system.repository;

import com.food.ordering.system.db.Util;
import com.food.ordering.system.dto.ProductDto;
import com.food.ordering.system.entity.Product;
import com.food.ordering.system.enums.ProductType;
import com.food.ordering.system.exceptions.ProductNotFoundException;
import com.food.ordering.system.mapper.ProductMapper;

import java.util.List;
import java.util.Optional;

public class ProductRepository {
    private static ProductRepository productRepository;

    private ProductRepository() {

    }

    public List<ProductDto> getAllproduct() {
        return Util.productTable
                .stream()
                .map(ProductMapper::productToProductDto)
                .toList();
    }

    public List<ProductDto> getProductsByType(ProductType productType) {
        return Util.productTable
                .stream()
                .filter(product -> product.getProductType() == productType)
                .map(ProductMapper::productToProductDto)
                .toList();
    }

    public Product getProductById(Long productId) {
        return Util.productTable
                .stream()
                .filter(product -> product.getId() == productId)
                .findAny()
                .orElseThrow(() -> new ProductNotFoundException("Product not found by id :" + productId));

    }

    public static ProductRepository getInstance() {
        return Optional.ofNullable(productRepository).orElse(new ProductRepository());
    }
}
