package com.food.ordering.system.service.impl;

import com.food.ordering.system.db.Util;
import com.food.ordering.system.dto.BasketDto;
import com.food.ordering.system.dto.OrderDto;
import com.food.ordering.system.dto.ProductDto;
import com.food.ordering.system.entity.Basket;
import com.food.ordering.system.entity.Order;
import com.food.ordering.system.entity.Product;
import com.food.ordering.system.enums.ProductType;
import com.food.ordering.system.exceptions.BasketIsAlreadyEmptyException;
import com.food.ordering.system.exceptions.NotEnoughMoneyException;
import com.food.ordering.system.exceptions.OrderNotFoundException;
import com.food.ordering.system.mapper.BasketMapper;
import com.food.ordering.system.mapper.OrderMapper;
import com.food.ordering.system.repository.*;
import com.food.ordering.system.service.inter.ShopService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShopServiceImpl implements ShopService {
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final BasketRepository basketRepository;
    private final BasketOrderRepository basketOrderRepository;
    private static ShopServiceImpl shopService;


    private ShopServiceImpl(ProductRepository productRepository,
                            ClientRepository clientRepository,
                            OrderRepository orderRepository,
                            BasketRepository basketRepository,
                            BasketOrderRepository basketOrderRepository) {
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.basketRepository = basketRepository;
        this.basketOrderRepository = basketOrderRepository;
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
        return orderRepository.insert(product, quantity);
    }

    @Override
    public boolean addToBasket(Long clientId, Order order) {
        var client = clientRepository.findById(clientId);
        var basket = basketRepository.findByClientId(clientId);
        if(basket.isEmpty()){
             basket = Optional.ofNullable(basketRepository.insertEmptyBasket(clientId));
        }
        basketOrderRepository.insert(basket.get(), order);
        this.changeTotalAmountOfBasket(basket.get());
        return true;

    }

    @Override
    public boolean checkOut(Long clientId) {

        var client = clientRepository.findById(clientId);
        var budget = client.getBudget();
        var basket = basketRepository.findByClientId(clientId).get();
        var basketAmount = basket.getTotalAmount();
        if (budget.compareTo(basketAmount) >= 0) {
            basket.setActive(false);
            basketRepository.update(basket);
            clientRepository.updateBudgetByClientId(clientId, budget.subtract(basketAmount));
            return true;
        } else
            throw new NotEnoughMoneyException("Your budget is: " + budget + " but basket total amount is: " + basketAmount);
    }

    @Override
    public boolean clearBasket(Long clientId) {
        var client = clientRepository.findById(clientId);
        var basket = basketRepository.findByClientId(clientId);
        if(basket.isEmpty())
            throw new BasketIsAlreadyEmptyException("Basket is already empty ! ! !");
        basket.get().setActive(false);
        basketRepository.update(basket.get());
        return true;
    }

    @Override
    public boolean removeOrderFromBasket(Long clientId, String removingOrderName) {
        var basket = basketRepository.findByClientId(clientId);
        var orderList = basketOrderRepository.getOrderListByBasketId(basket.get().getId());
        var order = orderList
                .stream()
                .filter(orderItem -> orderItem.getProduct().getName().equalsIgnoreCase(removingOrderName))
                .findAny()
                .orElseThrow(() -> new OrderNotFoundException("Order not found with product name:" + removingOrderName));
        orderRepository.removeOrder(order.getId());
        changeTotalAmountOfBasket(basket.get());
        return true;
    }

    @Override
    public void changeTotalAmountOfBasket(Basket basket) {
        var totalBasketAmount = basketOrderRepository.getBasketTotalAmountByClientId(basket.getId());
        basket.setTotalAmount(totalBasketAmount);
        basketRepository.update(basket);
    }

    @Override
    public BasketDto getBasketInfo(Long clientId) {
        var basket = basketRepository.findByClientId(clientId);
        if(basket.isEmpty())
            return BasketDto.EMPTY;
        var basketDto = BasketMapper.toDto(basket.get());

        var orderList = basketOrderRepository.getOrderListByBasketId(basketDto.getId())
                .stream()
                .map(OrderMapper::toDto)
                .toList();
        basketDto.setOrderList(orderList);
        return basketDto;
    }

    public static ShopServiceImpl getInstance(ProductRepository productRepository,
                                              ClientRepository clientRepository,
                                              OrderRepository orderRepository,
                                              BasketRepository basketRepository,
                                              BasketOrderRepository basketOrderRepository) {
        return Optional.ofNullable(shopService).orElse(new ShopServiceImpl(productRepository,
                clientRepository,
                orderRepository,
                basketRepository,
                basketOrderRepository));
    }
}
