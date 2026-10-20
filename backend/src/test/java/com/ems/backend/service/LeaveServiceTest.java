package com.ems.backend.service;

import com.ems.backend.dto.LeaveDto;
import com.ems.backend.dto.Response;
import com.ems.backend.entity.Employee;
import com.ems.backend.entity.Leave;
import com.ems.backend.repository.EmployeeRepository;
import com.ems.backend.repository.LeaveRepository;
import com.ems.backend.service.impl.LeaveServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveServiceTest {

    @Mock
    private LeaveRepository leaveRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private LeaveServiceImpl leaveService;

    private Employee employee;
    private Leave leave;
    private LeaveDto leaveDto;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Alice");
        employee.setLastName("Smith");

        leave = new Leave();
        leave.setId(100L);
        leave.setLeaveType("SICK");
        leave.setStartDate(LocalDate.of(2026, 3, 1));
        leave.setEndDate(LocalDate.of(2026, 3, 3));
        leave.setTotalDays(3);
        leave.setReason("Flu");
        leave.setStatus("PENDING");
        leave.setEmployee(employee);

        leaveDto = new LeaveDto();
        leaveDto.setEmployeeId(1L);
        leaveDto.setLeaveType("SICK");
        leaveDto.setStartDate(LocalDate.of(2026, 3, 1));
        leaveDto.setEndDate(LocalDate.of(2026, 3, 3));
        leaveDto.setReason("Flu");
    }

    @Test
    @DisplayName("getAllLeaves should return list of leaves")
    void testGetAllLeaves_Success() {
        when(leaveRepository.findAll()).thenReturn(List.of(leave));

        Response response = leaveService.getAllLeaves();

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("applyLeave should save leave and calculate totalDays correctly")
    void testApplyLeave_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(leaveRepository.save(any(Leave.class))).thenAnswer(invocation -> {
            Leave l = invocation.getArgument(0);
            l.setId(100L);
            return l;
        });

        Response response = leaveService.applyLeave(leaveDto);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("Leave applied successfully");
        verify(leaveRepository, times(1)).save(any(Leave.class));
    }

    @Test
    @DisplayName("applyLeave should return 404 when employee does not exist")
    void testApplyLeave_EmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Response response = leaveService.applyLeave(leaveDto);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(404);
        assertThat(response.getMessage()).isEqualTo("Employee not found");
        verify(leaveRepository, never()).save(any());
    }

    @Test
    @DisplayName("approveLeave should set status to APPROVED")
    void testApproveLeave_Success() {
        when(leaveRepository.findById(100L)).thenReturn(Optional.of(leave));
        when(leaveRepository.save(any(Leave.class))).thenReturn(leave);

        Response response = leaveService.approveLeave(100L);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(leave.getStatus()).isEqualTo("APPROVED");
        verify(leaveRepository, times(1)).save(leave);
    }

    @Test
    @DisplayName("approveLeave should return 404 when leave not found")
    void testApproveLeave_NotFound() {
        when(leaveRepository.findById(999L)).thenReturn(Optional.empty());

        Response response = leaveService.approveLeave(999L);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("rejectLeave should set status to REJECTED")
    void testRejectLeave_Success() {
        when(leaveRepository.findById(100L)).thenReturn(Optional.of(leave));
        when(leaveRepository.save(any(Leave.class))).thenReturn(leave);

        Response response = leaveService.rejectLeave(100L);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(leave.getStatus()).isEqualTo("REJECTED");
        verify(leaveRepository, times(1)).save(leave);
    }

    @Test
    @DisplayName("rejectLeave should return 404 when leave not found")
    void testRejectLeave_NotFound() {
        when(leaveRepository.findById(999L)).thenReturn(Optional.empty());

        Response response = leaveService.rejectLeave(999L);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("getLeaveByEmployee should return leaves for employee")
    void testGetLeaveByEmployee_Success() {
        when(leaveRepository.findByEmployeeId(1L)).thenReturn(List.of(leave));

        Response response = leaveService.getLeaveByEmployee(1L);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("getLeaveByDepartment should return leaves for department")
    void testGetLeaveByDepartment_Success() {
        when(leaveRepository.findByEmployeeDepartmentId(5L)).thenReturn(List.of(leave));

        Response response = leaveService.getLeaveByDepartment(5L);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("getLeaveBetweenDates should return leaves in range")
    void testGetLeaveBetweenDates_Success() {
        LocalDate start = LocalDate.of(2026, 3, 1);
        LocalDate end = LocalDate.of(2026, 3, 31);
        when(leaveRepository.findByStartDateBetween(start, end)).thenReturn(List.of(leave));

        Response response = leaveService.getLeaveBetweenDates(start, end);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getCount()).isEqualTo(1);
    }
}
