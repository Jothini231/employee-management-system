package com.ems.backend.controller;

import com.ems.backend.dto.DepartmentDto;
import com.ems.backend.dto.Response;
import com.ems.backend.service.DepartmentService;
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
class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("GET /api/departments should return 200 with all departments")
    void testGetAllDepartments() throws Exception {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(1L);
        dto.setName("HR");

        Response response = Response.success("Departments retrieved successfully")
                .withData(List.of(dto))
                .withCount(1);

        when(departmentService.getAllDepartments()).thenReturn(response);

        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Departments retrieved successfully"))
                .andExpect(jsonPath("$.count").value(1));
    }

    @Test
    @DisplayName("GET /api/departments/{id} should return 200 with department")
    void testGetDepartmentById() throws Exception {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(1L);
        dto.setName("HR");

        Response response = Response.success("Department retrieved successfully").withData(dto);
        when(departmentService.getDepartmentById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("HR"));
    }

    @Test
    @DisplayName("POST /api/departments should return 200 on creation")
    void testCreateDepartment() throws Exception {
        DepartmentDto dto = new DepartmentDto();
        dto.setName("Finance");
        dto.setDepartmentCode("FIN-01");

        Response response = Response.success("Department created").withData(dto);
        when(departmentService.createDepartment(any(DepartmentDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Department created"));
    }

    @Test
    @DisplayName("PUT /api/departments/{id} should return 200 on update")
    void testUpdateDepartment() throws Exception {
        DepartmentDto dto = new DepartmentDto();
        dto.setName("Updated HR");

        Response response = Response.success("Department updated successfully").withData(dto);
        when(departmentService.updateDepartment(eq(1L), any(DepartmentDto.class))).thenReturn(response);

        mockMvc.perform(put("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Department updated successfully"));
    }

    @Test
    @DisplayName("DELETE /api/departments/{id} should return 200 on deletion")
    void testDeleteDepartment() throws Exception {
        Response response = Response.success("Department deleted successfully");
        when(departmentService.deleteDepartment(1L)).thenReturn(response);

        mockMvc.perform(delete("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Department deleted successfully"));
    }
}
