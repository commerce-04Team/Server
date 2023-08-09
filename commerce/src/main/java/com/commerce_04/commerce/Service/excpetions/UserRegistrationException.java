package com.commerce_04.commerce.Service.excpetions;


public class UserRegistrationException extends RuntimeException {
    public UserRegistrationException(String message) {
        super(message);
    }
}
