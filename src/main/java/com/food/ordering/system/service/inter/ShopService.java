package com.food.ordering.system.service.inter;

import com.food.ordering.system.dto.ProductDto;
import com.food.ordering.system.entity.Client;
import com.food.ordering.system.entity.Order;
import com.food.ordering.system.entity.Product;
import com.food.ordering.system.enums.ProductType;

import java.util.List;

public interface ShopService {

    List<ProductDto> getProducts();

    List<ProductDto> getProductsByProductType(ProductType productType);

    Order generateOrder(Long productId, Integer quantity);

    boolean addToBasket(Long clientId, Order order);

    boolean checkOut(Long clientId);

    String getBasketInfo(Long clientId);

    boolean clearBasket(Long clientId);

    boolean removeOrderFromBasket(Long clientId, String removingOrderName);
}
