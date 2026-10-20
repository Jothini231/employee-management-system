package com.ems.backend.mapper;

import com.ems.backend.dto.DepartmentDto;
import com.ems.backend.entity.Department;
import com.ems.backend.entity.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DepartmentMapperTest {

    @Test
    @DisplayName("toDto should map Department entity to DepartmentDto with manager and count")
    void testToDto() {
        Department dept = new Department();
        dept.setId(1L);
        dept.setName("HR");
        dept.setDepartmentCode("HR-01");
        dept.setDescription("Human Resources");
        dept.setStatus(true);

        Employee manager = new Employee();
        manager.setId(2L);
        manager.setFirstName("Boss");
        dept.setManager(manager);

        Employee emp1 = new Employee();
        Employee emp2 = new Employee();
        dept.setEmployees(List.of(emp1, emp2));

        DepartmentDto dto = DepartmentMapper.toDto(dept);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("HR");
        assertThat(dto.getDepartmentCode()).isEqualTo("HR-01");
        assertThat(dto.getEmployeeCount()).isEqualTo(2);
        assertThat(dto.getManager()).isNotNull();
        assertThat(dto.getManager().getId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("toDto should return null when department is null")
    void testToDto_Null() {
        assertThat(DepartmentMapper.toDto(null)).isNull();
    }

    @Test
    @DisplayName("toEntity should map DepartmentDto to Department entity")
    void testToEntity() {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(10L);
        dto.setName("Finance");
        dto.setDepartmentCode("FIN-01");
        dto.setDescription("Finance Dept");
        dto.setStatus(true);

        Department dept = DepartmentMapper.toEntity(dto);

        assertThat(dept).isNotNull();
        assertThat(dept.getId()).isEqualTo(10L);
        assertThat(dept.getName()).isEqualTo("Finance");
        assertThat(dept.getDepartmentCode()).isEqualTo("FIN-01");
    }

    @Test
    @DisplayName("toDepartmentDtoList should map list correctly")
    void testToDepartmentDtoList() {
        Department dept = new Department();
        dept.setId(1L);
        dept.setName("IT");

        List<DepartmentDto> dtos = DepartmentMapper.toDepartmentDtoList(List.of(dept));

        assertThat(dtos).hasSize(1);
        assertThat(dtos.get(0).getName()).isEqualTo("IT");
    }
}
