package com.ems.backend.service;

import com.ems.backend.dto.DashboardStatsDto;
import com.ems.backend.dto.EmployeePerDepartmentDto;
import com.ems.backend.dto.MonthlySalaryExpenseDto;
import com.ems.backend.dto.Response;
import com.ems.backend.entity.Employee;
import com.ems.backend.entity.Leave;
import com.ems.backend.repository.DepartmentRepository;
import com.ems.backend.repository.EmployeeRepository;
import com.ems.backend.repository.LeaveRepository;
import com.ems.backend.repository.SalaryRepository;
import com.ems.backend.service.impl.DashboardServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private LeaveRepository leaveRepository;

    @Mock
    private SalaryRepository salaryRepository;

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    @Test
    @DisplayName("getDashboardStats should return calculated statistics")
    void testGetDashboardStats_Success() {
        when(employeeRepository.count()).thenReturn(25L);
        when(departmentRepository.count()).thenReturn(4L);
        when(leaveRepository.countByStatusIgnoreCase("Approved")).thenReturn(2L);
        when(salaryRepository.sumNetSalaryByMonth(anyString())).thenReturn(150000.0);

        Response response = dashboardService.getDashboardStats();

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getData()).isInstanceOf(DashboardStatsDto.class);

        DashboardStatsDto stats = (DashboardStatsDto) response.getData();
        assertThat(stats.getTotalEmployees()).isEqualTo(25L);
        assertThat(stats.getTotalDepartments()).isEqualTo(4L);
        assertThat(stats.getEmployeesOnLeave()).isEqualTo(2L);
        assertThat(stats.getMonthlySalaryExpense()).isEqualTo(150000.0);
    }

    @Test
    @DisplayName("getEmployeesPerDepartment should return list from repository")
    void testGetEmployeesPerDepartment_Success() {
        EmployeePerDepartmentDto dto = new EmployeePerDepartmentDto("Engineering", 10L);
        when(employeeRepository.countEmployeesPerDepartment()).thenReturn(List.of(dto));

        Response response = dashboardService.getEmployeesPerDepartment();

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("getLeaveStatistics should return counts for each status")
    void testGetLeaveStatistics_Success() {
        when(leaveRepository.countByStatusIgnoreCase("Approved")).thenReturn(5L);
        when(leaveRepository.countByStatusIgnoreCase("Pending")).thenReturn(3L);
        when(leaveRepository.countByStatusIgnoreCase("Rejected")).thenReturn(1L);

        Response response = dashboardService.getLeaveStatistics();

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getCount()).isEqualTo(3);
    }

    @Test
    @DisplayName("getMonthlySalaryExpenses should return expenses list")
    void testGetMonthlySalaryExpenses_Success() {
        MonthlySalaryExpenseDto dto = new MonthlySalaryExpenseDto("2026-03", 50000.0);
        when(salaryRepository.calculateMonthlySalaryExpenses()).thenReturn(List.of(dto));

        Response response = dashboardService.getMonthlySalaryExpenses();

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("getRecentActivities should return combined recent employee and leave events")
    void testGetRecentActivities_Success() {
        Employee employee = new Employee();
        employee.setFirstName("Bob");
        employee.setLastName("Ross");
        employee.setDateOfJoining(LocalDate.now());

        Leave leave = new Leave();
        leave.setEmployee(employee);
        leave.setStartDate(LocalDate.now());
        leave.setStatus("Approved");

        when(employeeRepository.findTop5ByOrderByIdDesc()).thenReturn(List.of(employee));
        when(leaveRepository.findTop5ByOrderByIdDesc()).thenReturn(List.of(leave));

        Response response = dashboardService.getRecentActivities();

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("getNotifications should return pending leave requests and new employees")
    void testGetNotifications_Success() {
        Employee employee = new Employee();
        employee.setFirstName("Bob");
        employee.setLastName("Ross");
        employee.setDateOfJoining(LocalDate.now());

        Leave leave = new Leave();
        leave.setEmployee(employee);
        leave.setStartDate(LocalDate.now());
        leave.setStatus("Pending");

        when(leaveRepository.findTop5ByOrderByIdDesc()).thenReturn(List.of(leave));
        when(employeeRepository.findTop5ByOrderByIdDesc()).thenReturn(List.of(employee));

        Response response = dashboardService.getNotifications();

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getCount()).isEqualTo(2);
    }
}
