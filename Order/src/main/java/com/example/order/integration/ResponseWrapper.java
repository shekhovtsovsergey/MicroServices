package com.example.order.integration;

public class ResponseWrapper<T> {
    private T result;
    private boolean isError;

    public ResponseWrapper(T result, boolean isError) {
        this.result = result;
        this.isError = isError;
    }

    public static <T> ResponseWrapper<T> ok(T result) {
        return new ResponseWrapper<>(result, false);
    }

    public static <T> ResponseWrapper<T> error() {
        return new ResponseWrapper<>(null, true);
    }

    public T unwrap() {
        return this.result;
    }

    public boolean isError() {
        return isError;
    }
}
