package com.payvyne.demoApi.exception;

public class ValidationExeption extends RuntimeException {

    public ValidationExeption(String errorMessage) {

        super(errorMessage);
    }
}
