package com.food.ordering.system.view;

import com.food.ordering.system.controller.ClientController;
import com.food.ordering.system.controller.ShopController;
import com.food.ordering.system.db.Util;
import com.food.ordering.system.dto.ClientDto;
import com.food.ordering.system.entity.Client;
import com.food.ordering.system.enums.Gender;
import com.food.ordering.system.enums.ProductType;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;

public class Index {
    private final ClientController clientController;
    private final ShopController shopController;

    private final Scanner input;
    private Client client;

    private static Index index;

    private Index(ClientController clientController, ShopController shopController, Scanner input) {
        this.clientController = clientController;
        this.shopController = shopController;
        this.input = input;

    }

    public void initialize() {
        while (true) menu();
    }

    private void menu() {
        System.out.println("1.Login \n2.Registration");
        var value = input.nextInt();
        if (value == 1) menuForLogin();
        else menuForRegistration();

    }

    private void menuForLogin() {
        System.out.println("Username:");
        var userName = input.next();
        System.out.println("Password:");
        var password = input.next();
        this.client = clientController.login(userName, password);
        while (true) {
            System.out.println("1.Shop");
            System.out.println("2.Basket");
            System.out.println("3.Check out");
            System.out.println("4.Personal info");
            System.out.println("5.Exit");
            var operationForLoginMenu = input.nextInt();
            if (operationForLoginMenu == 1) {
                menuForShop();
            } else if (operationForLoginMenu == 2) {
                printBasketMenu();
            } else if (operationForLoginMenu == 3) {
                shopController.checkOut(this.client.getId());
                System.out.println("Successfully finished.Your budget :" + client.getBudget());
            } else if (operationForLoginMenu == 4) {
                System.out.println(this.client);
            } else {
                System.exit(0);
            }
        }
    }

    private void printBasketMenu() {
        System.out.println("1.Print basket info");
        System.out.println("2.Clear basket");
        System.out.println("3.Remove order from basket");

        var operationForBasket = input.nextInt();
        if (operationForBasket == 1) {
            var basketInfo = shopController.getBasketInfo(client.getId());
            System.out.println(basketInfo);
        } else if (operationForBasket == 2) {
            shopController.clearBasket(client.getId());
            System.out.println("Basket is empty");
        } else if (operationForBasket == 3) {
            var basketInfo = shopController.getBasketInfo(client.getId());
            System.out.println(basketInfo);
            System.out.println("Enter the product name");
            var removingOrderName = input.next();
            shopController.removeOrderFromBasket(client.getId(), removingOrderName);
            System.out.println(removingOrderName + " removed from basket");
            System.out.println("----------------------");
        }
    }


    private void menuForShop() {
        System.out.println("1.Get all products");
        System.out.println("2.Get products by product type");
        var operationForShop = input.nextInt();
        if (operationForShop == 1) {
            printAllProducts();
            shop();
        } else {
            menuForProductsByType();
            shop();
        }


    }

    private void shop() {
        System.out.println("Enter the product id");
        var choosenProduct = input.nextLong();
        System.out.println("Enter the quantity");
        var quantity = input.nextInt();
        var order = shopController.generateOrder(choosenProduct, quantity);
        var result = shopController.addToBasket(this.client.getId(), order);
        if (result) System.out.println("Order succesfully added to basket");
    }

    private void printAllProducts() {
        shopController.getProducts()
                .stream()
                .forEach(System.out::println);
    }

    private void menuForProductsByType() {
        System.out.println("1.Watch");
        System.out.println("2.Drinks");
        System.out.println("3.Eat");
        System.out.println("4.Phone");
        var operationForType = input.nextInt();
        switch (operationForType) {
            case 1 -> shopController.getProductsByProductType(ProductType.WATCH).forEach(System.out::println);
            case 2 -> shopController.getProductsByProductType(ProductType.DRINKS).forEach(System.out::println);
            case 3 -> shopController.getProductsByProductType(ProductType.EAT).forEach(System.out::println);
            default -> shopController.getProductsByProductType(ProductType.PHONE).forEach(System.out::println);
        }
    }


    private void menuForRegistration() {
        System.out.println("Name:");
        var name = input.next();

        System.out.println("Surname:");
        var surname = input.next();


        System.out.println("Gender: ");
        var gender = Gender.valueOf(input.next().toUpperCase());

        System.out.println("Budget:");
        var budget = new BigDecimal(input.next());

        System.out.println("Username:");
        var userName = input.next();

        System.out.println("Password:");
        var password = input.next();

        System.out.println("Repeat password:");
        var repeatedPassword = input.next();

        var clientDto = new ClientDto(
                clientController.getMaxClientId() + 1L, name, surname,  gender, budget, userName, password, repeatedPassword
        );

        System.out.println(clientController.registration(clientDto));
    }

    public static Index getInstance(ClientController clientController, ShopController shopController, Scanner input) {
        return Optional.ofNullable(index).orElse(new Index(clientController, shopController, input));
    }

}
