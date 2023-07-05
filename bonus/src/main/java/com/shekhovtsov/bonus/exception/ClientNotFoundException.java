package com.shekhovtsov.bonus.exception;

public class ClientNotFoundException extends ObjectNotFoundException {

    public ClientNotFoundException(Long clientId) {
        super(String.format("Client id %s not found", clientId));
    }

    public ClientNotFoundException(String message) {
        super(message);
    }
}
