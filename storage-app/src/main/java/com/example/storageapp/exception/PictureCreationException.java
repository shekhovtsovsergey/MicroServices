package com.example.storageapp.exception;

public class PictureCreationException extends ObjectNotFoundException {

    public PictureCreationException(Long id) {
        super(String.format("Picture id %s not created", id));
    }

    public PictureCreationException(String message) {
        super(message);
    }
}
