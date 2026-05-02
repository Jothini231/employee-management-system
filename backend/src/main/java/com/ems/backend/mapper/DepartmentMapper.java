package com.ems.backend.mapper;

import com.ems.backend.dto.DepartmentDto;
import com.ems.backend.dto.EmployeeDto;
import com.ems.backend.entity.Department;
import com.ems.backend.entity.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentMapper {

    public static DepartmentDto toDto(Department dept){
        if(dept == null){
            return null;
        }

        DepartmentDto dto = new DepartmentDto();
        dto.setId(dept.getId());
        dto.setName(dept.getName());
        dto.setDepartmentCode(dept.getDepartmentCode());
        dto.setDescription(dept.getDescription());
        dto.setStatus(dept.getStatus());
        if(dept.getManager() != null){
            dto.setManager(EmployeeMapper.toDto(dept.getManager()));
        }
        dto.setEmployeeCount(
                dept.getEmployees() != null ? dept.getEmployees().size() : 0
        );

        return dto;
    }

    public static Department toEntity(DepartmentDto dto){
        if(dto == null){
            return null;
        }

        Department dept = new Department();
        dept.setId(dto.getId());
        dept.setName(dto.getName());
        dept.setDepartmentCode(dto.getDepartmentCode());
        dept.setDescription(dto.getDescription());
        dept.setStatus(dto.getStatus());
        return dept;
    }

    public static List<DepartmentDto> toDepartmentDtoList(List<Department> departments){
        return departments.stream()
                .map(DepartmentMapper::toDto)
                .collect(Collectors.toList());
    }
}
