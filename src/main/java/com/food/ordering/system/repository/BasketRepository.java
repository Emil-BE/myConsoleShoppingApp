package com.food.ordering.system.repository;

import com.food.ordering.system.config.Config;
import com.food.ordering.system.db.DecimalUtils;
import com.food.ordering.system.entity.Basket;
import com.food.ordering.system.entity.Client;
import com.food.ordering.system.entity.Order;
import com.food.ordering.system.entity.Product;
import com.food.ordering.system.enums.Gender;
import com.food.ordering.system.exceptions.ClietnNotFoundException;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.util.Optional;

public class BasketRepository {
    private BasketRepository(){}

    public static BasketRepository basketRepository = null;

    public Optional<Basket> findByClientId(Long clientId) {
        Basket basket = null;
        try (var connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER_NAME, Config.DB_PASSWORD)) {
            var prepareStatement = connection.prepareStatement(
                    "select id,  total_amount, is_active from basket where is_active and client_id = ?");
            prepareStatement.setLong(1, clientId);
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                var id = resultSet.getLong(1);
                var totalAmount = resultSet.getBigDecimal(2);
                var isActive = resultSet.getBoolean(3);
                basket = new Basket(id, clientId, totalAmount, isActive);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(basket);

    }

    public Basket insertEmptyBasket(Long clientId){
        return insert(clientId, BigDecimal.ZERO);
    }

    public Basket insert(Long clientId, BigDecimal totalAmount) {
        try (
                var connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER_NAME, Config.DB_PASSWORD);
        ) {

            var preparedStatement = connection.prepareStatement(
                    "insert into basket(id, client_id, total_amount, is_active)" +
                            "values(?, ?, ?, ?)");
            var id = 1L + getMaxId();
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, clientId);
            preparedStatement.setBigDecimal(3, totalAmount);
            preparedStatement.setBoolean(4, true);
            preparedStatement.execute();
            return new Basket(id, clientId, totalAmount, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Long getMaxId() {
        var maxId = 0L;
        try (var connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER_NAME, Config.DB_PASSWORD)) {

            var statement = connection.createStatement();
            var sql = "select max(id) from basket";
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                maxId = resultSet.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return maxId;
    }

    public void update(Basket basket){
        try (var connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER_NAME, Config.DB_PASSWORD)) {
            var prepareStatement = connection.prepareStatement(
                    "update basket set id = ?, client_id = ?, total_amount = ?, is_active = ? where id = ?");
            prepareStatement.setLong(1, basket.getId());
            prepareStatement.setLong(2, basket.getClientId());
            prepareStatement.setBigDecimal(3, basket.getTotalAmount());
            prepareStatement.setBoolean(4,basket.isActive());
            prepareStatement.setLong(5, basket.getId());
            prepareStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static BasketRepository getInstance(){
        return Optional.ofNullable(basketRepository).orElse(new BasketRepository());
    }
}
