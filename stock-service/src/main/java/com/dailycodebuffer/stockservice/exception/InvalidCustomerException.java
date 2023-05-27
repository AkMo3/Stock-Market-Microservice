package com.dailycodebuffer.stockservice.exception;

import java.util.NoSuchElementException;

public class InvalidCustomerException extends NoSuchElementException {
    public InvalidCustomerException(String message) {
        super(message);
    }
}
