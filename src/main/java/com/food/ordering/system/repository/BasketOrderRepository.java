package com.food.ordering.system.repository;

import com.food.ordering.system.config.Config;
import com.food.ordering.system.entity.Basket;
import com.food.ordering.system.entity.Order;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.util.Optional;

public class BasketOrderRepository {
    private BasketOrderRepository(){}

    private static BasketOrderRepository basketOrderRepository = null;

    public void insert(Basket basket, Order order) {
        try (
                var connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER_NAME, Config.DB_PASSWORD);
        ) {

            var preparedStatement = connection.prepareStatement(
                    "insert into basket_order(basket_id, order_id,  is_active)" +
                            "values(?, ?, ?)");
            preparedStatement.setLong(1, basket.getId());
            preparedStatement.setLong(2, order.getId());
            preparedStatement.setBoolean(3, true);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BigDecimal getBasketTotalAmountByClientId(Long basketId) {
        var totalAmount = BigDecimal.ZERO;
        try (
                var connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER_NAME, Config.DB_PASSWORD);
        ) {

            var preparedStatement = connection.prepareStatement(
                    " select COALESCE(sum(p.price * o.quantity), 0) from basket_order bo " +
                            " join orders o on bo.order_id = o.id " +
                            " join product p on p.id = o.product_id " +
                            " where o.is_active and bo.is_active and bo.basket_id = ? ");
            preparedStatement.setLong(1, basketId);
            var resultSet =         preparedStatement.executeQuery();
            if(resultSet.next())
                totalAmount = resultSet.getBigDecimal(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalAmount;
    }


    public static BasketOrderRepository getInstance(){
        return Optional.ofNullable(basketOrderRepository)
                .orElse(new BasketOrderRepository());
    }

}
