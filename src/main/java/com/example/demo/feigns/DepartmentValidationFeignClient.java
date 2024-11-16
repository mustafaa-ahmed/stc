package com.example.demo.feigns;

import com.example.demo.dto.DepartmentValidationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "departmentValidationClient", url = "https://thirdparty-department-validation.com/api")
public interface DepartmentValidationFeignClient {

    @GetMapping("/validate")
    DepartmentValidationResponseDTO validateDepartment(@RequestParam("department") String department);
}
