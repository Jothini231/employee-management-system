package com.ems.backend.service;

import com.ems.backend.dto.ChangePasswordDto;
import com.ems.backend.dto.EmployeeDto;
import com.ems.backend.dto.Response;
import com.ems.backend.dto.UpdateProfileDto;
import com.ems.backend.entity.Department;
import com.ems.backend.entity.Employee;
import com.ems.backend.entity.Role;
import com.ems.backend.repository.DepartmentRepository;
import com.ems.backend.repository.EmployeeRepository;
import com.ems.backend.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private Department department;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setId(1L);
        department.setName("IT");
        department.setDepartmentCode("IT-01");

        employee = new Employee();
        employee.setId(10L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setEmployeeCode("EMP-1001");
        employee.setRole(Role.EMPLOYEE);
        employee.setPassword("secret123");
        employee.setDepartment(department);

        employeeDto = new EmployeeDto();
        employeeDto.setId(10L);
        employeeDto.setFirstName("John");
        employeeDto.setLastName("Doe");
        employeeDto.setEmail("john.doe@example.com");
        employeeDto.setEmployeeCode("EMP-1001");
        employeeDto.setRole(Role.EMPLOYEE);
        employeeDto.setDepartmentId(1L);
    }

    @Test
    @DisplayName("getAllEmployees should return all employees successfully")
    void testGetAllEmployees_Success() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        Response response = employeeService.getAllEmployees();

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getCount()).isEqualTo(1);
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getEmployeeById should return employee when found")
    void testGetEmployeeById_Found() {
        when(employeeRepository.findById(10L)).thenReturn(Optional.of(employee));

        Response response = employeeService.getEmployeeById(10L);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getData()).isNotNull();
        verify(employeeRepository, times(1)).findById(10L);
    }

    @Test
    @DisplayName("getEmployeeById should return 404 when employee does not exist")
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        Response response = employeeService.getEmployeeById(99L);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(404);
        assertThat(response.getMessage()).contains("No employee found with id : 99");
    }

    @Test
    @DisplayName("createEmployee should return 400 when input DTO is null")
    void testCreateEmployee_NullDto() {
        Response response = employeeService.createEmployee(null);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.getMessage()).isEqualTo("Employee details required");
    }

    @Test
    @DisplayName("createEmployee should return 409 when employeeCode already exists")
    void testCreateEmployee_CodeExists() {
        when(employeeRepository.existsByEmployeeCode("EMP-1001")).thenReturn(true);

        Response response = employeeService.createEmployee(employeeDto);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(409);
        verify(employeeRepository, never()).save(any());
    }

    @Test
    @DisplayName("createEmployee should return 404 when departmentId is invalid")
    void testCreateEmployee_DepartmentNotFound() {
        when(employeeRepository.existsByEmployeeCode("EMP-1001")).thenReturn(false);
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        Response response = employeeService.createEmployee(employeeDto);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(404);
        assertThat(response.getMessage()).contains("Department not found with id: 1");
        verify(employeeRepository, never()).save(any());
    }

    @Test
    @DisplayName("createEmployee should succeed with valid data and department")
    void testCreateEmployee_Success() {
        when(employeeRepository.existsByEmployeeCode("EMP-1001")).thenReturn(false);
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> {
            Employee emp = invocation.getArgument(0);
            emp.setId(10L);
            return emp;
        });

        Response response = employeeService.createEmployee(employeeDto);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("Employee created successfully");
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("updateEmployee should update fields and return success")
    void testUpdateEmployee_Success() {
        when(employeeRepository.findById(10L)).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        employeeDto.setFirstName("UpdatedJohn");
        Response response = employeeService.updateEmployee(10L, employeeDto);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    @DisplayName("updateEmployee should return 404 if employee does not exist")
    void testUpdateEmployee_NotFound() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        Response response = employeeService.updateEmployee(99L, employeeDto);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("deleteEmployee should delete existing employee")
    void testDeleteEmployee_Success() {
        when(employeeRepository.findById(10L)).thenReturn(Optional.of(employee));

        Response response = employeeService.deleteEmployee(10L);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    @DisplayName("deleteEmployee should return 404 for non-existent employee")
    void testDeleteEmployee_NotFound() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        Response response = employeeService.deleteEmployee(99L);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(404);
        verify(employeeRepository, never()).delete(any());
    }

    @Test
    @DisplayName("getEmployeesByDepartmentId should return list when department exists")
    void testGetEmployeesByDepartmentId_Success() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(employeeRepository.findByDepartmentId(1L)).thenReturn(List.of(employee));

        Response response = employeeService.getEmployeesByDepartmentId(1L);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("getEmployeesByDepartmentId should return 404 if department does not exist")
    void testGetEmployeesByDepartmentId_DeptNotFound() {
        when(departmentRepository.findById(99L)).thenReturn(Optional.empty());

        Response response = employeeService.getEmployeesByDepartmentId(99L);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("updateProfile should update employee profile details")
    void testUpdateProfile_Success() {
        when(employeeRepository.findById(10L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        UpdateProfileDto updateDto = new UpdateProfileDto();
        updateDto.setFirstName("Johnny");
        updateDto.setLastName("Bravo");
        updateDto.setEmail("johnny@test.com");

        Response response = employeeService.updateProfile(10L, updateDto);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(employee.getFirstName()).isEqualTo("Johnny");
    }

    @Test
    @DisplayName("changePassword should update password when credentials match")
    void testChangePassword_Success() {
        when(employeeRepository.findById(10L)).thenReturn(Optional.of(employee));

        ChangePasswordDto changeDto = new ChangePasswordDto();
        changeDto.setCurrentPassword("secret123");
        changeDto.setNewPassword("newSecret456");
        changeDto.setConfirmPassword("newSecret456");

        Response response = employeeService.changePassword(10L, changeDto);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(employee.getPassword()).isEqualTo("newSecret456");
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    @DisplayName("changePassword should fail when current password is wrong")
    void testChangePassword_WrongCurrentPassword() {
        when(employeeRepository.findById(10L)).thenReturn(Optional.of(employee));

        ChangePasswordDto changeDto = new ChangePasswordDto();
        changeDto.setCurrentPassword("wrongPassword");
        changeDto.setNewPassword("newSecret456");
        changeDto.setConfirmPassword("newSecret456");

        Response response = employeeService.changePassword(10L, changeDto);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.getMessage()).isEqualTo("Incorrect current password");
    }

    @Test
    @DisplayName("changePassword should fail when new and confirm passwords do not match")
    void testChangePassword_Mismatch() {
        when(employeeRepository.findById(10L)).thenReturn(Optional.of(employee));

        ChangePasswordDto changeDto = new ChangePasswordDto();
        changeDto.setCurrentPassword("secret123");
        changeDto.setNewPassword("newSecret456");
        changeDto.setConfirmPassword("differentPassword");

        Response response = employeeService.changePassword(10L, changeDto);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.getMessage()).isEqualTo("New password and confirm password do not match");
    }
}
