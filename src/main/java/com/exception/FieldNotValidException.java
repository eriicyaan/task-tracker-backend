package com.exception;

public class FieldNotValidException extends RuntimeException {
    public FieldNotValidException(String message) {
        super(message);
    }
}
