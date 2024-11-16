package com.example.demo.service.impl;

import com.example.demo.controller.EmployeeController;
import com.example.demo.dto.EmailValidationResponseDTO;
import com.example.demo.feigns.EmailValidationFeignClient;
import com.example.demo.model.Employee;
import com.example.demo.service.EmailNotificationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailNotificationServiceImpl implements EmailNotificationService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private static final String API_KEY = "c94126465c40e3e464368eedee7e3355e697ba2e";

    private final EmailValidationFeignClient emailValidationFeignClient;
    private final JavaMailSender mailSender;

    @Async
    public void sendEmailNotification(Employee employee) {
        if (isValidEmail(employee.getEmail())) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(employee.getEmail());
            message.setSubject("Employee Creation Successful");
            message.setText("Hello,\n\nEmployee " + employee.getFirstName() + " has been successfully created.\n\nBest regards,\nYour Company");
            mailSender.send(message);
        }
    }

    public boolean isValidEmail(String email) {
        try {
            EmailValidationResponseDTO response = emailValidationFeignClient.validateEmail(email, API_KEY);

            if (response.getData() != null) {
                String status = response.getData().getStatus();
                logger.info("Email Validation Status: {}, Result: {}", status, response.getData().getResult());
                return "valid".equalsIgnoreCase(status);
            } else {
                logger.warn("Email validation returned null data for email: {}", email);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error occurred while validating email: {}", email, e);
            return false;
        }
    }

}
