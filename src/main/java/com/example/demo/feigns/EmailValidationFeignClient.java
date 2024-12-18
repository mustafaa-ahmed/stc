package com.example.demo.feigns;

import com.example.demo.dto.EmailValidationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "emailValidationClient", url = "https://api.hunter.io/v2")
public interface EmailValidationFeignClient {

    @GetMapping("/email-verifier")
    EmailValidationResponseDTO validateEmail(@RequestParam("email") String email, @RequestParam("api_key") String apiKey);
}
