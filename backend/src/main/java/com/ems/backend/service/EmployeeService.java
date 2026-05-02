package com.ems.backend.service;

import com.ems.backend.dto.EmployeeDto;
import com.ems.backend.dto.Response;

public interface EmployeeService {

     Response getAllEmployees();
     Response getEmployeeById(Long id);
     Response createEmployee(EmployeeDto employeeDto);
     Response updateEmployee(Long id,EmployeeDto employeeDto);
     Response deleteEmployee(Long id);
     Response getEmployeesByDepartmentId(Long deptId);
}
