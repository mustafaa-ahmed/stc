package com.example.demo.exception;

import lombok.Getter;

@Getter
public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }
}
