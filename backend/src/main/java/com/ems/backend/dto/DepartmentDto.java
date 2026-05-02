package com.ems.backend.dto;

import com.ems.backend.entity.Employee;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

    private Long id;
    private String name;
    private String departmentCode;
    private String description;
    private Boolean status;
    private Long managerId;
    private EmployeeDto manager;
    private Integer employeeCount;
}