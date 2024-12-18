package com.example.demo;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createEmployee_ShouldReturnCreatedStatus() throws Exception {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setFirstName("Mustafa");
        employee.setLastName("Ahmed");
        employee.setEmail("mustafaa.fcih@gmail.com");
        employee.setDepartment("IT");
        employee.setSalary(BigDecimal.valueOf(1000.0));

        Mockito.when(employeeService.createEmployee(Mockito.any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Mustafa")))
                .andExpect(jsonPath("$.lastName", is("Ahmed")))
                .andExpect(jsonPath("$.email", is("mustafaa.fcih@gmail.com")))
                .andExpect(jsonPath("$.department", is("IT")))
                .andExpect(jsonPath("$.salary", is(1000.0)));
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee() throws Exception {
        UUID employeeId = UUID.randomUUID();
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setFirstName("Mustafa");
        employee.setLastName("Ahmed");
        employee.setEmail("mustafaa.fcih@gmail.com");
        employee.setDepartment("HR");
        employee.setSalary(BigDecimal.valueOf(1200.0));

        Mockito.when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.of(employee));

        mockMvc.perform(get("/api/employees/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Mustafa")))
                .andExpect(jsonPath("$.lastName", is("Ahmed")))
                .andExpect(jsonPath("$.email", is("mustafaa.fcih@gmail.com")))
                .andExpect(jsonPath("$.department", is("HR")))
                .andExpect(jsonPath("$.salary", is(1200.0)));
    }

    @Test
    void updateEmployee_ShouldReturnUpdatedEmployee() throws Exception {
        UUID employeeId = UUID.randomUUID();
        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(employeeId);
        updatedEmployee.setFirstName("Mustafa");
        updatedEmployee.setLastName("Ahmed");
        updatedEmployee.setEmail("mustafaa.fcih@gmail.com");
        updatedEmployee.setDepartment("Finance");
        updatedEmployee.setSalary(BigDecimal.valueOf(1500.0));

        Mockito.when(employeeService.updateEmployee(Mockito.eq(employeeId), Mockito.any(Employee.class))).thenReturn(updatedEmployee);

        mockMvc.perform(put("/api/employees/{id}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Mustafa")))
                .andExpect(jsonPath("$.lastName", is("Ahmed")))
                .andExpect(jsonPath("$.email", is("mustafaa.fcih@gmail.com")))
                .andExpect(jsonPath("$.department", is("Finance")))
                .andExpect(jsonPath("$.salary", is(1500.0)));
    }

    @Test
    void deleteEmployee_ShouldReturnNoContent() throws Exception {
        UUID employeeId = UUID.randomUUID();
        Mockito.doNothing().when(employeeService).deleteEmployee(employeeId);

        mockMvc.perform(delete("/api/employees/{id}", employeeId))
                .andExpect(status().isNoContent());
    }

    @Test
    void listAllEmployees_ShouldReturnEmployeeList() throws Exception {
        Employee employee1 = new Employee();
        employee1.setId(UUID.randomUUID());
        employee1.setFirstName("Mustafa");
        employee1.setLastName("Ahmed");
        employee1.setEmail("mustafaa.fcih@gmail.com");
        employee1.setDepartment("Marketing");
        employee1.setSalary(BigDecimal.valueOf(1100.0));

        Employee employee2 = new Employee();
        employee2.setId(UUID.randomUUID());
        employee2.setFirstName("Bob");
        employee2.setLastName("White");
        employee2.setEmail("bob.white@example.com");
        employee2.setDepartment("Sales");
        employee2.setSalary(BigDecimal.valueOf(1300.0));

        Mockito.when(employeeService.listAllEmployees()).thenReturn(java.util.List.of(employee1, employee2));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].firstName", is("Mustafa")))
                .andExpect(jsonPath("$[1].firstName", is("Bob")));
    }
}
