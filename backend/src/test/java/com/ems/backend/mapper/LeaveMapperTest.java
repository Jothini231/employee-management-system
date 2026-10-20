package com.ems.backend.mapper;

import com.ems.backend.dto.LeaveDto;
import com.ems.backend.entity.Employee;
import com.ems.backend.entity.Leave;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LeaveMapperTest {

    @Test
    @DisplayName("toDto should map Leave entity to LeaveDto")
    void testToDto() {
        Employee employee = new Employee();
        employee.setId(5L);

        Leave leave = new Leave();
        leave.setId(1L);
        leave.setLeaveType("CASUAL");
        leave.setReason("Personal");
        leave.setStartDate(LocalDate.of(2026, 4, 1));
        leave.setEndDate(LocalDate.of(2026, 4, 2));
        leave.setStatus("PENDING");
        leave.setTotalDays(2);
        leave.setEmployee(employee);

        LeaveDto dto = LeaveMapper.toDto(leave);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getLeaveType()).isEqualTo("CASUAL");
        assertThat(dto.getEmployeeId()).isEqualTo(5L);
        assertThat(dto.getTotalDays()).isEqualTo(2);
    }

    @Test
    @DisplayName("toDto should return null when leave is null")
    void testToDto_Null() {
        assertThat(LeaveMapper.toDto(null)).isNull();
    }

    @Test
    @DisplayName("toLeaveDtoList should map list of leaves")
    void testToLeaveDtoList() {
        Employee employee = new Employee();
        employee.setId(5L);

        Leave leave = new Leave();
        leave.setId(1L);
        leave.setEmployee(employee);

        List<LeaveDto> dtos = LeaveMapper.toLeaveDtoList(List.of(leave));

        assertThat(dtos).hasSize(1);
        assertThat(dtos.get(0).getId()).isEqualTo(1L);
    }
}
