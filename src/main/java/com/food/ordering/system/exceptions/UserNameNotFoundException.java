package com.food.ordering.system.exceptions;

public class UserNameNotFoundException extends RuntimeException{

    public UserNameNotFoundException(String message) {
        super(message);
    }
}
