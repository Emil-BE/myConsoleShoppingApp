package com.food.ordering.system.db;

import com.food.ordering.system.entity.Client;
import com.food.ordering.system.entity.Product;
import com.food.ordering.system.enums.ProductType;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public Util() {

    }


    public static List<Client> clientTable = new ArrayList<>();
    public static List<Product> productTable = new ArrayList<>();


    public static void initializeProducts() {
        var product1 = new Product(1L, "Rolex", DecimalUtils.TWO_HUNDRED, ProductType.WATCH);
        var product2 = new Product(2L, "Casio", DecimalUtils.HUNDRED, ProductType.WATCH);
        var product3 = new Product(3L, "Tissot", DecimalUtils.FIVE_HUNDRED, ProductType.WATCH);

        var product4 = new Product(4L, "Coca-Cola", DecimalUtils.ONE_POINT_FIFTY, ProductType.DRINKS);
        var product5 = new Product(5L, "Fanta", DecimalUtils.TWO_POINT_FIFTY, ProductType.DRINKS);
        var product6 = new Product(6L, "Sprite", DecimalUtils.THREE_POINT_FIFTY, ProductType.DRINKS);

        var product7 = new Product(7L, "Lays", DecimalUtils.THREE, ProductType.EAT);
        var product8 = new Product(8L, "ETI", DecimalUtils.ZERO_POINT_FIFTY, ProductType.EAT);
        var product9 = new Product(9L, "Kinder", DecimalUtils.TWO, ProductType.EAT);

        var product10 = new Product(10L, "Iphone", DecimalUtils.THREE_THOUSAND, ProductType.PHONE);
        var product11 = new Product(11L, "Samsung", DecimalUtils.TWO_THOUSAND, ProductType.PHONE);
        var product12 = new Product(12L, "Nokia", DecimalUtils.THOUSAND, ProductType.PHONE);
        productTable.add(product1);
        productTable.add(product2);
        productTable.add(product3);
        productTable.add(product4);
        productTable.add(product5);
        productTable.add(product6);
        productTable.add(product7);
        productTable.add(product8);
        productTable.add(product9);
        productTable.add(product10);
        productTable.add(product11);
        productTable.add(product12);


    }


}
