package com.commerce_04.commerce.Service.excpetions;

public class InValidValueException extends RuntimeException{
    public InValidValueException(String message) {
        super(message);
    }
}
