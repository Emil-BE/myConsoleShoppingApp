package com.food.ordering.system.service.impl;

import com.food.ordering.system.db.Util;
import com.food.ordering.system.dto.ProductDto;
import com.food.ordering.system.entity.Basket;
import com.food.ordering.system.entity.Order;
import com.food.ordering.system.entity.Product;
import com.food.ordering.system.enums.ProductType;
import com.food.ordering.system.exceptions.NotEnoughMoneyException;
import com.food.ordering.system.exceptions.OrderNotFoundException;
import com.food.ordering.system.repository.ClientRepository;
import com.food.ordering.system.repository.ProductRepository;
import com.food.ordering.system.service.inter.ShopService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShopServiceImpl implements ShopService {
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private static ShopServiceImpl shopService;

    static {
        Util.initializeProducts();
    }

    private ShopServiceImpl(ProductRepository productRepository, ClientRepository clientRepository) {
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<ProductDto> getProducts() {
        return productRepository.getAllproduct();
    }

    @Override
    public List<ProductDto> getProductsByProductType(ProductType productType) {
        return productRepository.getProductsByType(productType);
    }

    @Override
    public Order generateOrder(Long productId, Integer quantity) {
        var product = productRepository.getProductById(productId);
        return new Order(product, quantity);
    }

    @Override
    public boolean addToBasket(Long clientId, Order order) {
        var client = clientRepository.findById(clientId);
        var basket = client.getBasket();
        basket.getOrderList().add(order);
        var totalAmountBasket = basket.getOrderList()
                .stream()
                .map(orderItem -> orderItem.getProduct().getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        basket.setTotalAmount(totalAmountBasket);
        client.setBasket(basket);
        return true;

    }

    @Override
    public boolean checkOut(Long clientId) {
        var client = clientRepository.findById(clientId);
        var budget = client.getBudget();
        var basketAmount = client.getBasket().getTotalAmount();
        if (budget.compareTo(basketAmount) >= 0) {
            client.setBudget(budget.subtract(basketAmount));
            client.setBasket(new Basket(new ArrayList<Order>(), BigDecimal.ZERO));
            return true;
        } else
            throw new NotEnoughMoneyException("Your budget is: " + budget + " but basket total amount is: " + basketAmount);
    }

    @Override
    public String getBasketInfo(Long clientId) {
        var client = clientRepository.findById(clientId);
        return client.getBasket().toString();
    }

    @Override
    public boolean clearBasket(Long clientId) {
        var client = clientRepository.findById(clientId);
        client.setBasket(new Basket(new ArrayList<Order>(), BigDecimal.ZERO));
        return true;
    }

    @Override
    public boolean removeOrderFromBasket(Long clientId, String removingOrderName) {
        var client = clientRepository.findById(clientId);
        var basket = client.getBasket();
        var orderList = basket.getOrderList();
        var order = orderList
                .stream()
                .filter(orderItem -> orderItem.getProduct().getName().equalsIgnoreCase(removingOrderName))
                .findAny()
                .orElseThrow(() -> new OrderNotFoundException("Order not found with product name:" + removingOrderName));
        orderList.remove(order);
        basket.setTotalAmount(basket.getTotalAmount()
                .subtract(
                        order.getProduct().getPrice().multiply(BigDecimal.valueOf(order.getQuantity()))));

        return true;
    }

    public static ShopServiceImpl getInstance(ProductRepository productRepository, ClientRepository clientRepository) {
        return Optional.ofNullable(shopService).orElse(new ShopServiceImpl(productRepository, clientRepository));
    }
}
