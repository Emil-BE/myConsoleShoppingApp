package com.food.ordering.system.repository;

import com.food.ordering.system.config.Config;
import com.food.ordering.system.entity.Client;
import com.food.ordering.system.entity.Order;
import com.food.ordering.system.entity.Product;

import java.sql.DriverManager;
import java.util.Optional;

public class OrderRepository {
    private static OrderRepository orderRepository = null;
    private OrderRepository(){

    }


    public Order insert(Product product, Integer quantity) {
        try (var connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER_NAME, Config.DB_PASSWORD);
        ) {

            var preparedStatement = connection.prepareStatement(
                    "insert into orders(id, product_id, quantity, is_active)" +
                            "values(?, ?, ?, ?)");
            var id = 1L  + getMaxId();
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, product.getId());
            preparedStatement.setInt(3,quantity);
            preparedStatement.setBoolean(4, true);
            preparedStatement.execute();
            return new Order(id, product, quantity, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Long getMaxId() {
        var maxId = 0L;
        try (var connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER_NAME, Config.DB_PASSWORD)) {

            var statement = connection.createStatement();
            var sql = "select max(id) from orders";
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                maxId = resultSet.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return maxId;
    }

    public static OrderRepository getInstance() {
        return Optional.ofNullable(orderRepository).orElse(new OrderRepository());
    }


}
