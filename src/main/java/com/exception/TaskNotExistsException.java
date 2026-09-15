package com.exception;

public class TaskNotExistsException extends RuntimeException {
    public TaskNotExistsException(String message) {
        super(message);
    }
}
