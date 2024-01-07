package com.food.ordering.system.entity;

import com.food.ordering.system.enums.Gender;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Client {
    private Long id;
    private String name;
    private String surname;
    private Gender gender;
    private BigDecimal budget;
    private String username;
    private String password;
    private Basket basket;

    public Client(Long id, String name, String surname,  Gender gender, BigDecimal budget, String username, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.budget = budget;
        this.username = username;
        this.password = password;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }


    @Override
    public String toString() {
        return
                "id=" + id +
                        ", name='" + name + '\'' +
                        ", surname='" + surname + '\'' +
                        ", gender=" + gender +
                        ", budget=" + budget +
                        ", username='" + username + '\'' +
                        ", password='" + password + '\'';

    }
}
