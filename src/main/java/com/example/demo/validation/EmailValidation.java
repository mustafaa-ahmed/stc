package com.example.demo.validation;

import org.springframework.stereotype.Service;

@Service
public class EmailValidation {

    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
