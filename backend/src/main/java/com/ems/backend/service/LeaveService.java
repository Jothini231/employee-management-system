package com.ems.backend.service;

import com.ems.backend.dto.LeaveDto;
import com.ems.backend.dto.Response;

import java.time.LocalDate;

public interface LeaveService {

    Response getAllLeaves();

    Response applyLeave(LeaveDto leaveDto);

    Response approveLeave(Long id);

    Response rejectLeave(Long id);

    Response getLeaveByEmployee(Long employeeId);

    Response getLeaveByDepartment(Long departmentId);

    Response getLeaveBetweenDates(LocalDate start,LocalDate end);
}
