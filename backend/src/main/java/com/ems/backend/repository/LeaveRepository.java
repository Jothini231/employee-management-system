package com.ems.backend.repository;

import com.ems.backend.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave , Long> {

        List<Leave> findByEmployeeId(Long employeeId);

        List<Leave> findByEmployeeDepartmentId(Long departmentId);

        List<Leave> findByStartDateBetween(LocalDate start,LocalDate end);
}
