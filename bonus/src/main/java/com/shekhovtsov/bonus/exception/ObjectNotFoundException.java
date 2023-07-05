package com.shekhovtsov.bonus.exception;

public abstract class ObjectNotFoundException extends Exception {

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
