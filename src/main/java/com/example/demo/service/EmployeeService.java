package com.example.demo.service;

import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.exception.InvalidInputException;
import com.example.demo.model.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {
    Employee createEmployee(Employee employee) throws InvalidInputException;

    Optional<Employee> getEmployeeById(UUID id) throws EmployeeNotFoundException;

    Employee updateEmployee(UUID id, Employee employee) throws EmployeeNotFoundException;

    void deleteEmployee(UUID id) throws EmployeeNotFoundException;

    List<Employee> listAllEmployees();
}
