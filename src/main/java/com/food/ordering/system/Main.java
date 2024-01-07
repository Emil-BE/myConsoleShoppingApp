package com.food.ordering.system;

import com.food.ordering.system.controller.ClientController;
import com.food.ordering.system.controller.ShopController;
import com.food.ordering.system.repository.*;
import com.food.ordering.system.service.impl.ClientServiceImpl;
import com.food.ordering.system.service.impl.ShopServiceImpl;
import com.food.ordering.system.view.Index;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        var clientRepository = ClientRepository.getInstance();
        var clientService = ClientServiceImpl.getInstance(clientRepository);
        var clientController = ClientController.getInstance(clientService);

        var productRepository = ProductRepository.getInstance();
        var orderRepository = OrderRepository.getInstance();
        var basketRepository = BasketRepository.getInstance();
        var basketOrderRepository = BasketOrderRepository.getInstance();
        var shopService = ShopServiceImpl.getInstance(productRepository,
                clientRepository,
                orderRepository,
                basketRepository,
                basketOrderRepository);
        var shopController = ShopController.getInstance(shopService);

        var input = new Scanner(System.in);
        var view = Index.getInstance(clientController, shopController, input);
        view.initialize();
    }
}