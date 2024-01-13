package com.food.ordering.system.mapper;

import com.food.ordering.system.dto.BasketDto;
import com.food.ordering.system.dto.OrderDto;
import com.food.ordering.system.entity.Basket;
import com.food.ordering.system.entity.Order;

import java.util.Objects;

public class OrderMapper {
    public static OrderDto toDto(Order order) {
        return new OrderDto(order.getId(), order.getProduct(), order.getQuantity(), order.isActive());
    }

    public static Order toEntity(OrderDto orderDto){
        return new Order(orderDto.getId(), orderDto.getProduct(), orderDto.getQuantity(), orderDto.isActive());
    }
}
