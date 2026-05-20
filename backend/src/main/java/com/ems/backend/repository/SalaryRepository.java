package com.ems.backend.repository;

import com.ems.backend.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    @org.springframework.data.jpa.repository.Query("SELECT new com.ems.backend.dto.MonthlySalaryExpenseDto(s.month, SUM(s.netSalary)) " +
           "FROM Salary s GROUP BY s.month")
    java.util.List<com.ems.backend.dto.MonthlySalaryExpenseDto> calculateMonthlySalaryExpenses();

    @org.springframework.data.jpa.repository.Query("SELECT SUM(s.netSalary) FROM Salary s WHERE s.month = :month")
    Double sumNetSalaryByMonth(@org.springframework.data.repository.query.Param("month") String month);
}
