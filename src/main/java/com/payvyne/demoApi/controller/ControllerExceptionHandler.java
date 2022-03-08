package com.payvyne.demoApi.controller;

import com.payvyne.demoApi.exception.ValidationExeption;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

public class ControllerExceptionHandler {

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex) {
        return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ValidationExeption.class})
    public ResponseEntity<Object> handleValidationExeption(ValidationExeption ex) {
        return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
