package com.commerce_04.commerce.Service.excpetions;

public class NotAcceptException extends RuntimeException {
    public NotAcceptException(String message) {
        super(message);
    }
}
