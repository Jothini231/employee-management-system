package com.ems.backend.service;

import com.ems.backend.dto.Response;
import com.ems.backend.dto.SalaryDto;
import com.ems.backend.entity.Department;
import com.ems.backend.entity.Employee;
import com.ems.backend.entity.Salary;
import com.ems.backend.repository.EmployeeRepository;
import com.ems.backend.repository.SalaryRepository;
import com.ems.backend.service.impl.SalaryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaryServiceTest {

    @Mock
    private SalaryRepository salaryRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private SalaryServiceImpl salaryService;

    private Employee employee;
    private Department department;
    private Salary salary;
    private SalaryDto salaryDto;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setId(2L);
        department.setName("Finance");

        employee = new Employee();
        employee.setId(10L);
        employee.setFirstName("Bob");
        employee.setLastName("Builder");
        employee.setDepartment(department);

        salary = new Salary();
        salary.setId(50L);
        salary.setEmployee(employee);
        salary.setBasicSalary(50000.0);
        salary.setAllowance(5000.0);
        salary.setBonus(2000.0);
        salary.setDeduction(1000.0);
        salary.setNetSalary(56000.0);
        salary.setMonth("2026-03");

        salaryDto = new SalaryDto();
        salaryDto.setEmployeeId(10L);
        salaryDto.setBasicSalary(50000.0);
        salaryDto.setAllowance(5000.0);
        salaryDto.setBonus(2000.0);
        salaryDto.setDeduction(1000.0);
        salaryDto.setMonth("2026-03");
    }

    @Test
    @DisplayName("addSalary should calculate net salary and save successfully")
    void testAddSalary_Success() {
        when(employeeRepository.findById(10L)).thenReturn(Optional.of(employee));
        when(salaryRepository.save(any(Salary.class))).thenAnswer(inv -> {
            Salary s = inv.getArgument(0);
            s.setId(50L);
            return s;
        });

        Response response = salaryService.addSalary(salaryDto);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("Salary added successfully");
        verify(salaryRepository, times(1)).save(any(Salary.class));
    }

    @Test
    @DisplayName("addSalary should return 404 if employee does not exist")
    void testAddSalary_EmployeeNotFound() {
        when(employeeRepository.findById(10L)).thenReturn(Optional.empty());

        Response response = salaryService.addSalary(salaryDto);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(404);
        verify(salaryRepository, never()).save(any());
    }

    @Test
    @DisplayName("getAllSalaries should return all salary records")
    void testGetAllSalaries_Success() {
        when(salaryRepository.findAll()).thenReturn(List.of(salary));

        Response response = salaryService.getAllSalaries();

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("getSalaryById should return salary details when found")
    void testGetSalaryById_Success() {
        when(salaryRepository.findById(50L)).thenReturn(Optional.of(salary));

        Response response = salaryService.getSalaryById(50L);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("getSalaryById should return 404 when not found")
    void testGetSalaryById_NotFound() {
        when(salaryRepository.findById(999L)).thenReturn(Optional.empty());

        Response response = salaryService.getSalaryById(999L);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("updateSalary should update values when salary and employee exist")
    void testUpdateSalary_Success() {
        when(salaryRepository.findById(50L)).thenReturn(Optional.of(salary));
        when(employeeRepository.findById(10L)).thenReturn(Optional.of(employee));
        when(salaryRepository.save(any(Salary.class))).thenReturn(salary);

        salaryDto.setBonus(5000.0);
        Response response = salaryService.updateSalary(50L, salaryDto);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(salary.getNetSalary()).isEqualTo(59000.0);
        verify(salaryRepository, times(1)).save(salary);
    }

    @Test
    @DisplayName("updateSalary should return 404 if salary does not exist")
    void testUpdateSalary_SalaryNotFound() {
        when(salaryRepository.findById(999L)).thenReturn(Optional.empty());

        Response response = salaryService.updateSalary(999L, salaryDto);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("deleteSalary should delete existing salary")
    void testDeleteSalary_Success() {
        when(salaryRepository.findById(50L)).thenReturn(Optional.of(salary));

        Response response = salaryService.deleteSalary(50L);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        verify(salaryRepository, times(1)).delete(salary);
    }

    @Test
    @DisplayName("deleteSalary should return 404 when salary not found")
    void testDeleteSalary_NotFound() {
        when(salaryRepository.findById(999L)).thenReturn(Optional.empty());

        Response response = salaryService.deleteSalary(999L);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(404);
        verify(salaryRepository, never()).delete(any());
    }

    @Test
    @DisplayName("filterSalaries should filter by month, department, and employee")
    void testFilterSalaries_Success() {
        when(salaryRepository.findAll()).thenReturn(List.of(salary));

        Response response = salaryService.filterSalaries("2026-03", 2L, 10L);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("searchSalaries should search by employee first name")
    void testSearchSalaries_Success() {
        when(salaryRepository.findAll()).thenReturn(List.of(salary));

        Response response = salaryService.searchSalaries("bob");

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getCount()).isEqualTo(1);
    }
}
