package com.food.ordering.system.controller;

import com.food.ordering.system.dto.BasketDto;
import com.food.ordering.system.dto.ProductDto;
import com.food.ordering.system.entity.Client;
import com.food.ordering.system.entity.Order;
import com.food.ordering.system.enums.ProductType;
import com.food.ordering.system.mapper.BasketMapper;
import com.food.ordering.system.mapper.OrderMapper;
import com.food.ordering.system.service.inter.ClientService;
import com.food.ordering.system.service.inter.ShopService;
import com.food.ordering.system.service.impl.ShopServiceImpl;

import java.util.List;
import java.util.Optional;

public class ShopController {
    private ShopService shopService;
    private static ShopController shopController;

    private ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    public List<ProductDto> getProducts() {
        return this.shopService.getProducts();
    }

    public List<ProductDto> getProductsByProductType(ProductType productType) {
        return this.shopService.getProductsByProductType(productType);
    }

    public Order generateOrder(Long productId, Integer productQuantity) {
        return shopService.generateOrder(productId, productQuantity);
    }

    public boolean addToBasket(Long clientId, Order order) {
        return shopService.addToBasket(clientId, order);
    }

    public boolean checkOut(Long clientId) {
        return shopService.checkOut(clientId);
    }

    public boolean clearBasket(Long clientId) {
        return shopService.clearBasket(clientId);
    }

    public boolean removeOrderFromBasket(Long clientId, String removingOrderName) {
        return shopService.removeOrderFromBasket(clientId, removingOrderName);
    }

    public BasketDto getBasketInfo(Long clientId) {
        return shopService.getBasketInfo(clientId);
    }

    public static ShopController getInstance(ShopService shopService) {
        return Optional.ofNullable(shopController)
                .orElse(new ShopController(shopService));

    }


}
