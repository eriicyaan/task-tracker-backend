package com.tasktracker.handler.exception;

public class TaskNotExistsException extends RuntimeException {
    public TaskNotExistsException(String message) {
        super(message);
    }
}
