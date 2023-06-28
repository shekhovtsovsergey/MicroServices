package com.shekhovtsov.storageapp.exception;

public class PictureNotFoundException extends ObjectNotFoundException {

    public PictureNotFoundException(Long id) {
        super(String.format("Picture id %s not found", id));
    }

    public PictureNotFoundException(String message) {
        super(message);
    }
}
