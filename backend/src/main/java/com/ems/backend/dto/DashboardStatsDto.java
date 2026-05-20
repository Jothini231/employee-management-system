package com.ems.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatsDto {
    private long totalEmployees;
    private long totalDepartments;
    private long employeesOnLeave;
    private double monthlySalaryExpense;
}
