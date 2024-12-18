package com.example.demo.service.impl;

import com.example.demo.controller.EmployeeController;
import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.exception.InvalidInputException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmailNotificationService;
import com.example.demo.service.EmployeeService;
import com.example.demo.validation.DepartmentValidation;
import com.example.demo.validation.EmailValidation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeRepository employeeRepository;
    private final EmailValidation emailValidation;
    private final EmailNotificationService emailNotificationService;
    private final DepartmentValidation departmentValidation;
//    private final DepartmentValidationFeignClient departmentValidationFeignClient;

    @Override
    @Transactional
    public Employee createEmployee(Employee employee) {
        validateEmployee(employee);
        Employee savedEmployee = employeeRepository.save(employee);
        emailNotificationService.sendEmailNotification(savedEmployee);
        return savedEmployee;
    }

    @Override
    public Optional<Employee> getEmployeeById(UUID id) {
        return employeeRepository.findById(id).or(() -> {
            throw new EmployeeNotFoundException("Employee not found with ID: " + id);
        });
    }

    @Override
    @Transactional
    public Employee updateEmployee(UUID id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + id));

        validateEmployee(employee);

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setSalary(employee.getSalary());

        return employeeRepository.save(existingEmployee);
    }

    @Override
    @Transactional
    public void deleteEmployee(UUID id) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + id));
        employeeRepository.delete(existingEmployee);
    }

    @Override
    public List<Employee> listAllEmployees() {
        return employeeRepository.findAll();
    }

    private void validateEmployee(Employee employee) {
        if (!emailValidation.isValidEmail(employee.getEmail())) {
            logger.error("Invalid email: {}", employee.getEmail());
            throw new InvalidInputException("Invalid email: " + employee.getEmail());
        }

//        departmentValidationFeignClient.validateDepartment(employee.getDepartment());

        if (!departmentValidation.isValidDepartment(employee.getDepartment())) {
            logger.error("Invalid department: {}", employee.getDepartment());
            throw new InvalidInputException("Invalid department: " + employee.getDepartment());
        }
    }
}
