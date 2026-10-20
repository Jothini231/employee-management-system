package com.ems.backend.service;

import com.ems.backend.dto.DepartmentDto;
import com.ems.backend.dto.Response;
import com.ems.backend.entity.Department;
import com.ems.backend.entity.Employee;
import com.ems.backend.repository.DepartmentRepository;
import com.ems.backend.repository.EmployeeRepository;
import com.ems.backend.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private Department department;
    private DepartmentDto departmentDto;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setId(1L);
        department.setName("Engineering");
        department.setDepartmentCode("ENG-01");
        department.setDescription("Engineering Department");
        department.setStatus(true);

        departmentDto = new DepartmentDto();
        departmentDto.setId(1L);
        departmentDto.setName("Engineering");
        departmentDto.setDepartmentCode("ENG-01");
        departmentDto.setDescription("Engineering Department");
        departmentDto.setStatus(true);
    }

    @Test
    @DisplayName("createDepartment should fail when dto is null")
    void testCreateDepartment_Null() {
        Response response = departmentService.createDepartment(null);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.getMessage()).isEqualTo("Department data required");
    }

    @Test
    @DisplayName("createDepartment should fail when department code already exists")
    void testCreateDepartment_CodeExists() {
        when(departmentRepository.existsByDepartmentCode("ENG-01")).thenReturn(true);

        Response response = departmentService.createDepartment(departmentDto);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(409);
        verify(departmentRepository, never()).save(any());
    }

    @Test
    @DisplayName("createDepartment should create and return new department")
    void testCreateDepartment_Success() {
        when(departmentRepository.existsByDepartmentCode("ENG-01")).thenReturn(false);
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        Response response = departmentService.createDepartment(departmentDto);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("Department created");
    }

    @Test
    @DisplayName("getAllDepartments should return all departments")
    void testGetAllDepartments_Success() {
        when(departmentRepository.findAll()).thenReturn(List.of(department));

        Response response = departmentService.getAllDepartments();

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("getDepartmentById should return department if found")
    void testGetDepartmentById_Found() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        Response response = departmentService.getDepartmentById(1L);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("getDepartmentById should return 404 when not found")
    void testGetDepartmentById_NotFound() {
        when(departmentRepository.findById(99L)).thenReturn(Optional.empty());

        Response response = departmentService.getDepartmentById(99L);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("updateDepartment should update fields when department exists")
    void testUpdateDepartment_Success() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        departmentDto.setName("Updated Engineering");
        Response response = departmentService.updateDepartment(1L, departmentDto);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    @DisplayName("updateDepartment should return 404 when department not found")
    void testUpdateDepartment_NotFound() {
        when(departmentRepository.findById(99L)).thenReturn(Optional.empty());

        Response response = departmentService.updateDepartment(99L, departmentDto);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("deleteDepartment should delete department when found")
    void testDeleteDepartment_Success() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        Response response = departmentService.deleteDepartment(1L);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        verify(departmentRepository, times(1)).delete(department);
    }

    @Test
    @DisplayName("deleteDepartment should return 404 when not found")
    void testDeleteDepartment_NotFound() {
        when(departmentRepository.findById(99L)).thenReturn(Optional.empty());

        Response response = departmentService.deleteDepartment(99L);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(404);
        verify(departmentRepository, never()).delete(any());
    }

    @Test
    @DisplayName("assignManager should assign manager when employee belongs to the department")
    void testAssignManager_Success() {
        Employee manager = new Employee();
        manager.setId(5L);
        manager.setDepartment(department);

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(employeeRepository.findById(5L)).thenReturn(Optional.of(manager));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        Response response = departmentService.assignManager(1L, 5L);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(department.getManager()).isEqualTo(manager);
    }

    @Test
    @DisplayName("assignManager should fail when employee is not in department")
    void testAssignManager_NotInDepartment() {
        Department otherDept = new Department();
        otherDept.setId(2L);

        Employee manager = new Employee();
        manager.setId(5L);
        manager.setDepartment(otherDept);

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(employeeRepository.findById(5L)).thenReturn(Optional.of(manager));

        Response response = departmentService.assignManager(1L, 5L);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(404);
        assertThat(response.getMessage()).isEqualTo("Employee not in this department");
    }

    @Test
    @DisplayName("assignManager should fail when department not found")
    void testAssignManager_DeptNotFound() {
        when(departmentRepository.findById(99L)).thenReturn(Optional.empty());

        Response response = departmentService.assignManager(99L, 5L);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(404);
    }
}
