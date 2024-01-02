package com.food.ordering.system.exceptions;

public class ClietnNotFoundException extends RuntimeException{
    public ClietnNotFoundException(String message) {
        super(message);
    }

    public ClietnNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
