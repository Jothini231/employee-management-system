package com.ems.backend.controller;

import com.ems.backend.dto.EmployeeDto;
import com.ems.backend.dto.Response;
import com.ems.backend.entity.Role;
import com.ems.backend.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("GET /api/employees should return 200 with all employees")
    void testGetAllEmployees() throws Exception {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(1L);
        dto.setFirstName("Alice");
        dto.setLastName("Green");
        dto.setEmail("alice@example.com");

        Response response = Response.success("Employees retrieved successfully")
                .withData(List.of(dto))
                .withCount(1);

        when(employeeService.getAllEmployees()).thenReturn(response);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Employees retrieved successfully"))
                .andExpect(jsonPath("$.count").value(1));
    }

    @Test
    @DisplayName("GET /api/employees/{id} should return 200 with employee details")
    void testGetEmployeeById_Found() throws Exception {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(1L);
        dto.setFirstName("Alice");

        Response response = Response.success("Employee retrieved successfully").withData(dto);
        when(employeeService.getEmployeeById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.firstName").value("Alice"));
    }

    @Test
    @DisplayName("GET /api/employees/{id} should return 404 when employee not found")
    void testGetEmployeeById_NotFound() throws Exception {
        Response response = Response.error("No employee found with id : 99", 404);
        when(employeeService.getEmployeeById(99L)).thenReturn(response);

        mockMvc.perform(get("/api/employees/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No employee found with id : 99"));
    }

    @Test
    @DisplayName("POST /api/employees should return 200 on employee creation")
    void testCreateEmployee_Success() throws Exception {
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName("Bob");
        dto.setLastName("Jones");
        dto.setEmail("bob@example.com");
        dto.setRole(Role.EMPLOYEE);

        Response response = Response.success("Employee created successfully").withData(dto);
        when(employeeService.createEmployee(any(EmployeeDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Employee created successfully"));
    }

    @Test
    @DisplayName("PUT /api/employees/{id} should return 200 on update")
    void testUpdateEmployee_Success() throws Exception {
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName("UpdatedBob");

        Response response = Response.success("Employee updated successfully").withData(dto);
        when(employeeService.updateEmployee(eq(1L), any(EmployeeDto.class))).thenReturn(response);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Employee updated successfully"));
    }

    @Test
    @DisplayName("DELETE /api/employees/{id} should return 200 on deletion")
    void testDeleteEmployee_Success() throws Exception {
        Response response = Response.success("Employee deleted successfully");
        when(employeeService.deleteEmployee(1L)).thenReturn(response);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Employee deleted successfully"));
    }
}
