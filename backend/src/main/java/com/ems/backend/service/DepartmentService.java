package com.ems.backend.service;

import com.ems.backend.dto.DepartmentDto;
import com.ems.backend.dto.Response;

public interface DepartmentService {

    Response createDepartment(DepartmentDto dto);

    Response getAllDepartments();

    Response getDepartmentById(Long id);

    Response updateDepartment(Long id,DepartmentDto departmentDto);

    Response deleteDepartment(Long id);

    Response assignManager(Long deptId,Long managerId);
}
