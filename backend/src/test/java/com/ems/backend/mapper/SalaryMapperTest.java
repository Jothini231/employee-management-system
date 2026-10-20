package com.ems.backend.mapper;

import com.ems.backend.dto.SalaryResponseDto;
import com.ems.backend.entity.Employee;
import com.ems.backend.entity.Salary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SalaryMapperTest {

    @Test
    @DisplayName("toDto should map Salary entity to SalaryResponseDto")
    void testToDto() {
        Employee employee = new Employee();
        employee.setId(10L);

        Salary salary = new Salary();
        salary.setId(1L);
        salary.setEmployee(employee);
        salary.setBasicSalary(60000.0);
        salary.setAllowance(6000.0);
        salary.setBonus(3000.0);
        salary.setDeduction(2000.0);
        salary.setNetSalary(67000.0);
        salary.setMonth("2026-04");

        SalaryResponseDto dto = SalaryMapper.toDto(salary);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getEmployeeId()).isEqualTo(10L);
        assertThat(dto.getBasicSalary()).isEqualTo(60000.0);
        assertThat(dto.getAllowance()).isEqualTo(6000.0);
        assertThat(dto.getBonus()).isEqualTo(3000.0);
        assertThat(dto.getDeduction()).isEqualTo(2000.0);
        assertThat(dto.getNetSalary()).isEqualTo(67000.0);
        assertThat(dto.getMonth()).isEqualTo("2026-04");
    }

    @Test
    @DisplayName("toDto should return null when salary is null")
    void testToDto_Null() {
        assertThat(SalaryMapper.toDto(null)).isNull();
    }

    @Test
    @DisplayName("toDtoList should map list of salaries")
    void testToDtoList() {
        Employee employee = new Employee();
        employee.setId(10L);

        Salary salary = new Salary();
        salary.setId(1L);
        salary.setEmployee(employee);

        List<SalaryResponseDto> dtos = SalaryMapper.toDtoList(List.of(salary));

        assertThat(dtos).hasSize(1);
        assertThat(dtos.get(0).getId()).isEqualTo(1L);
    }
}
