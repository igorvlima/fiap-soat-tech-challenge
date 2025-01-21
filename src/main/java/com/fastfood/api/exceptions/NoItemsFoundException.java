package com.fastfood.api.exceptions;

public class NoItemsFoundException extends RuntimeException {

    public NoItemsFoundException(String message) {
        super(message);
    }
}