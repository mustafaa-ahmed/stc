package com.example.demo.validation;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentValidation {
    private static final List<String> VALID_DEPARTMENTS = List.of("HR", "IT", "Finance", "Marketing", "Sales");

    public boolean isValidDepartment(String department) {
        return VALID_DEPARTMENTS.contains(department);
    }
}
