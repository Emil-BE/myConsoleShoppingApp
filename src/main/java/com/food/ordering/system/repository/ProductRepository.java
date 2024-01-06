package com.food.ordering.system.repository;

import com.food.ordering.system.config.Config;
import com.food.ordering.system.db.Util;
import com.food.ordering.system.dto.ProductDto;
import com.food.ordering.system.entity.Product;
import com.food.ordering.system.enums.ProductType;
import com.food.ordering.system.exceptions.ProductNotFoundException;
import com.food.ordering.system.mapper.ProductMapper;

import javax.swing.text.html.Option;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepository {
    private static ProductRepository productRepository;

    private ProductRepository() {

    }

    public List<ProductDto> getAllproduct() {
        var productList = new ArrayList<ProductDto>();
        try (var connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER_NAME, Config.DB_PASSWORD)) {

            var statement = connection.createStatement();
            var sql = "select id,name,price, product_type  from product";
            var resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                var id = resultSet.getLong(1);
                var name = resultSet.getString(2);
                var price = resultSet.getBigDecimal(3);
                var productType = ProductType.valueOf(resultSet.getString(4));
                var productDto = new ProductDto(id, name, price, productType);
                productList.add(productDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;

    }

    public List<ProductDto> getProductsByType(ProductType productType) {

        var productList = new ArrayList<ProductDto>();
        try (var connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER_NAME, Config.DB_PASSWORD)) {

            var preparedStatement = connection.prepareStatement(
                    "select id,name, price  from product where product_type = ? ");
            preparedStatement.setString(1, productType.name());
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                var id = resultSet.getLong(1);
                var name = resultSet.getString(2);
                var price = resultSet.getBigDecimal(3);
                var productDto = new ProductDto(id, name, price, productType);
                productList.add(productDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    public Product getProductById(Long productId) {
        Product product = null;
        try (var connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER_NAME, Config.DB_PASSWORD)) {

            var preparedStatement = connection.prepareStatement(
                    "select id,name, price, product_type  from product where id = ? ");
            preparedStatement.setLong(1, productId);
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                var id = resultSet.getLong(1);
                var name = resultSet.getString(2);
                var price = resultSet.getBigDecimal(3);
                var productType = ProductType.valueOf(resultSet.getString(4));
                product = new Product(id, name, price, productType);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return Optional.ofNullable(product)
                .orElseThrow(() -> new ProductNotFoundException("Product not found by id :" + productId));

    }

    public static ProductRepository getInstance() {
        return Optional.ofNullable(productRepository).orElse(new ProductRepository());
    }
}
