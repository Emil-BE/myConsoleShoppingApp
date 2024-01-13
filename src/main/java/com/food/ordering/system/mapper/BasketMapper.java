package com.food.ordering.system.mapper;

import com.food.ordering.system.dto.BasketDto;
import com.food.ordering.system.entity.Basket;

import java.util.Objects;

public class BasketMapper {
    public static BasketDto toDto(Basket basket) {
        if(Objects.isNull(basket)) return BasketDto.EMPTY;
        return new BasketDto(basket.getId(), basket.getClientId(), basket.getTotalAmount(), basket.isActive());
    }

    public static Basket toEntity(BasketDto basketDto){
        return new Basket(basketDto.getId(), basketDto.getClientId(), basketDto.getTotalAmount(), basketDto.isActive());
    }
}
