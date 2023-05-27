package com.dailycodebuffer.stockservice.exception;

import java.util.NoSuchElementException;

public class InvalidStockException extends NoSuchElementException {

    public InvalidStockException(String message) {
        super(message);
    }
}
