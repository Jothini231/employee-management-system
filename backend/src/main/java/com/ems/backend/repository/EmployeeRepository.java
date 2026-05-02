package com.ems.backend.repository;

import com.ems.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmployeeCode(String employeeCode);
    List<Employee> findByDepartmentId(Long departmentId);
}
