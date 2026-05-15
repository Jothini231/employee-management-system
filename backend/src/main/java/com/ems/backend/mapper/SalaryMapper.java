package com.ems.backend.mapper;

import com.ems.backend.dto.SalaryDto;
import com.ems.backend.dto.SalaryResponseDto;
import com.ems.backend.entity.Salary;

import java.util.List;
import java.util.stream.Collectors;

public class SalaryMapper {

    public static SalaryResponseDto toDto(Salary salary){

        if(salary == null){
            return null;
        }

        SalaryResponseDto dto = new SalaryResponseDto();
        dto.setId(salary.getId());
        dto.setEmployeeId(salary.getEmployee().getId());
        dto.setBasicSalary(salary.getBasicSalary());
        dto.setAllowance(salary.getAllowance());
        dto.setBonus(salary.getBonus());
        dto.setDeduction(salary.getDeduction());
        dto.setNetSalary(salary.getNetSalary());
        dto.setMonth(salary.getMonth());

        return dto;
    }

    public static List<SalaryResponseDto> toDtoList(List<Salary> salaries){
        return salaries.stream()
                .map(SalaryMapper::toDto)
                .collect(Collectors.toList());
    }
}
