package com.ems.backend.repository;

import com.ems.backend.dto.EmployeePerDepartmentDto;
import com.ems.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmployeeCode(String employeeCode);
    List<Employee> findByDepartmentId(Long departmentId);
    
    @org.springframework.data.jpa.repository.Query("SELECT new com.ems.backend.dto.EmployeePerDepartmentDto(d.name, COUNT(e.id)) " +
           "FROM Employee e JOIN e.department d GROUP BY d.name")
    List<EmployeePerDepartmentDto> countEmployeesPerDepartment();

    List<Employee> findTop5ByOrderByIdDesc();
}
