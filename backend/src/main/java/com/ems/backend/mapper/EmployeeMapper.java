package com.ems.backend.mapper;

import com.ems.backend.dto.EmployeeDto;
import com.ems.backend.entity.Employee;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static EmployeeDto toDto(Employee employee){
        if(employee == null){
            return null;
        }

        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setDateOfBirth(employee.getDateOfBirth());
        dto.setGender(employee.getGender());
        dto.setAddress(employee.getAddress());
        dto.setContactNumber(employee.getContactNumber());
        dto.setEmployeeCode(employee.getEmployeeCode());
        if (employee.getDepartment() != null) {
            dto.setDepartmentId(employee.getDepartment().getId());
        }
        dto.setDesignation(employee.getDesignation());
        dto.setRole(employee.getRole());
        dto.setDateOfJoining(employee.getDateOfJoining());
        dto.setReportingManager(employee.getReportingManager());
        dto.setSalary(employee.getSalary());
        dto.setBankAccountNumber(employee.getBankAccountNumber());
        dto.setUserName(employee.getUserName());
        dto.setPhoto(employee.getPhoto());
        dto.setStatus(employee.getStatus());
        return dto;
    }


    public static Employee toEntity(EmployeeDto dto) {
        if (dto == null) return null;

        Employee employee = new Employee();

        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setDateOfBirth(dto.getDateOfBirth());
        employee.setGender(dto.getGender());
        employee.setAddress(dto.getAddress());
        employee.setContactNumber(dto.getContactNumber());
        employee.setEmployeeCode(dto.getEmployeeCode());
        employee.setDesignation(dto.getDesignation());
        employee.setRole(dto.getRole());
        employee.setDateOfJoining(dto.getDateOfJoining());
        employee.setReportingManager(dto.getReportingManager());
        employee.setSalary(dto.getSalary());
        employee.setBankAccountNumber(dto.getBankAccountNumber());
        employee.setUserName(dto.getUserName());
        if (dto.getPassword() != null && !dto.getPassword().startsWith("$2a$")) {
            employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        } else {
            employee.setPassword(dto.getPassword());
        }
        employee.setPhoto(dto.getPhoto());
        employee.setStatus(dto.getStatus());

        return employee;
    }

    public static List<EmployeeDto> toEmployeeDtoList(List<Employee> employees){
        return employees.stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList());
    }

}
