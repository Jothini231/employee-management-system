package com.ems.backend.mapper;

import com.ems.backend.dto.EmployeeDto;
import com.ems.backend.entity.Department;
import com.ems.backend.entity.Employee;
import com.ems.backend.entity.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeMapperTest {

    @Test
    @DisplayName("toDto should map all Employee entity fields to EmployeeDto")
    void testToDto() {
        Department department = new Department();
        department.setId(5L);

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john@example.com");
        employee.setDateOfBirth(LocalDate.of(1990, 1, 1));
        employee.setGender("Male");
        employee.setAddress("123 Street");
        employee.setContactNumber("9876543210");
        employee.setEmployeeCode("EMP001");
        employee.setDepartment(department);
        employee.setDesignation("Developer");
        employee.setRole(Role.EMPLOYEE);
        employee.setDateOfJoining(LocalDate.of(2022, 5, 10));
        employee.setReportingManager("Manager Bob");
        employee.setSalary(75000.0);
        employee.setBankAccountNumber("ACC123456");
        employee.setUserName("johnd");
        employee.setPhoto("photo.png");
        employee.setStatus("ACTIVE");

        EmployeeDto dto = EmployeeMapper.toDto(employee);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getFirstName()).isEqualTo("John");
        assertThat(dto.getLastName()).isEqualTo("Doe");
        assertThat(dto.getEmail()).isEqualTo("john@example.com");
        assertThat(dto.getDepartmentId()).isEqualTo(5L);
        assertThat(dto.getRole()).isEqualTo(Role.EMPLOYEE);
        assertThat(dto.getSalary()).isEqualTo(75000.0);
    }

    @Test
    @DisplayName("toDto should return null when employee entity is null")
    void testToDto_Null() {
        assertThat(EmployeeMapper.toDto(null)).isNull();
    }

    @Test
    @DisplayName("toEntity should map EmployeeDto to Employee and encrypt password")
    void testToEntity() {
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName("Jane");
        dto.setLastName("Smith");
        dto.setEmail("jane@example.com");
        dto.setPassword("plainPass");
        dto.setRole(Role.ADMIN);

        Employee entity = EmployeeMapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getFirstName()).isEqualTo("Jane");
        assertThat(entity.getLastName()).isEqualTo("Smith");
        assertThat(entity.getEmail()).isEqualTo("jane@example.com");
        assertThat(entity.getRole()).isEqualTo(Role.ADMIN);
        // Password should be encoded (not plain text)
        assertThat(entity.getPassword()).isNotEqualTo("plainPass");
        assertThat(entity.getPassword()).startsWith("$2a$");
    }

    @Test
    @DisplayName("toEntity should return null when dto is null")
    void testToEntity_Null() {
        assertThat(EmployeeMapper.toEntity(null)).isNull();
    }

    @Test
    @DisplayName("toEmployeeDtoList should map list of entities")
    void testToEmployeeDtoList() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");

        List<EmployeeDto> dtos = EmployeeMapper.toEmployeeDtoList(List.of(employee));

        assertThat(dtos).hasSize(1);
        assertThat(dtos.get(0).getId()).isEqualTo(1L);
    }
}
