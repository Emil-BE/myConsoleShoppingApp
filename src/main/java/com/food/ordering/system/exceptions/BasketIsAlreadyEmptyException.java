package com.food.ordering.system.exceptions;

public class BasketIsAlreadyEmptyException extends RuntimeException {
    public BasketIsAlreadyEmptyException(String message) {
        super(message);
    }

    public BasketIsAlreadyEmptyException(String message, Throwable cause) {
        super(message, cause);
    }
}
