package com.ems.backend.dto;

import lombok.Data;

@Data
public class SalaryDto {

    private Long employeeId;

    private Double basicSalary;

    private Double allowance;

    private Double bonus;

    private Double deduction;

    private String month;
}
