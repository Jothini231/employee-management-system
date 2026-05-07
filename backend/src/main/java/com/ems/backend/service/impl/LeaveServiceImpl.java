package com.ems.backend.service.impl;

import com.ems.backend.dto.LeaveDto;
import com.ems.backend.dto.Response;
import com.ems.backend.entity.Employee;
import com.ems.backend.entity.Leave;
import com.ems.backend.mapper.LeaveMapper;
import com.ems.backend.repository.EmployeeRepository;
import com.ems.backend.repository.LeaveRepository;
import com.ems.backend.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {

    private LeaveRepository leaveRepository;

    private EmployeeRepository employeeRepository;

    @Autowired
    public LeaveServiceImpl(LeaveRepository leaveRepository ,EmployeeRepository employeeRepository){
        this.leaveRepository = leaveRepository;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public Response getAllLeaves() {

        try{
            List<Leave> leaves = leaveRepository.findAll();
            if(leaves.isEmpty()){
                return Response.error("No leave request found",404);
            }

            List<LeaveDto> leaveDtos = LeaveMapper.toLeaveDtoList(leaves);
            return Response.success("Leave requests retrieved successfully").withData(leaveDtos).withCount(leaveDtos.size());
        } catch (Exception e) {
            return Response.error("Error in retrieving leave requests", 500);
        }

    }

    @Override
    public Response applyLeave(LeaveDto leaveDto) {

        try {
            Employee employee = employeeRepository.findById(leaveDto.getEmployeeId()).orElse(null);
            if (employee == null) {
                return Response.error("Employee not found", 404);
            }

            Leave leave = new Leave();
            leave.setLeaveType(leaveDto.getLeaveType());
            leave.setStartDate(leaveDto.getStartDate());
            leave.setEndDate(leaveDto.getEndDate());
            int days = (int) ChronoUnit.DAYS.between(leaveDto.getStartDate(), leaveDto.getEndDate()) + 1;
            leave.setTotalDays(days);
            leave.setReason(leaveDto.getReason());
            leave.setStatus("PENDING");

            leave.setEmployee(employee);

            Leave savedLeave = leaveRepository.save(leave);

            return Response.success("Leave applied successfully").withData(LeaveMapper.toDto(savedLeave));
        } catch (Exception e) {
            return Response.error("Error in applying leave", 500);
        }
    }

    @Override
    public Response approveLeave(Long id) {
        try {
            Leave leave = leaveRepository.findById(id).orElse(null);
            if (leave == null) {
                return Response.error("Leave not found", 404);
            }

            leave.setStatus("APPROVED");

            Leave updatedLeave = leaveRepository.save(leave);

            return Response.success("Leave approved successfully").withData(LeaveMapper.toDto(updatedLeave));

        } catch (Exception e) {
            return Response.error("Error in approving leave", 500);
        }
    }

    @Override
    public Response rejectLeave(Long id) {
        try {
            Leave leave = leaveRepository.findById(id).orElse(null);
            if (leave == null) {
                return Response.error("Leave not found", 404);
            }

            leave.setStatus("REJECTED");

            Leave updatedLeave = leaveRepository.save(leave);

            return Response.success("Leave rejected successfully").withData(LeaveMapper.toDto(updatedLeave));

        } catch (Exception e) {
            return Response.error("Error in approving leave", 500);
        }
    }

    @Override
    public Response getLeaveByEmployee(Long employeeId) {

        try {

            List<Leave> leaves = leaveRepository.findByEmployeeId(employeeId);

            if (leaves.isEmpty()) {
                return Response.error("No leave request found with employee id : " + employeeId, 404);
            }

            List<LeaveDto> leaveDtoList = LeaveMapper.toLeaveDtoList(leaves);

            return Response.success("leave requests retrieved successfully").withData(leaveDtoList).withCount(leaveDtoList.size());
        } catch (Exception e) {
            return Response.error("Error in retrieving leave requests",500);
        }
    }

    @Override
    public Response getLeaveByDepartment(Long departmentId) {
        try {

            List<Leave> leaves = leaveRepository.findByEmployeeDepartmentId(departmentId);

            if (leaves.isEmpty()) {
                return Response.error("No leave request found with department id : " + departmentId, 404);
            }

            List<LeaveDto> leaveDtos = LeaveMapper.toLeaveDtoList(leaves);

            return Response.success("leave requests retrieved successfully").withData(leaveDtos).withCount(leaveDtos.size());
        } catch (Exception e) {
            return Response.error("Error in retrieving leave requests",500);
        }
    }

    @Override
    public Response getLeaveBetweenDates(LocalDate start, LocalDate end) {
        try {

            List<Leave> leaves = leaveRepository.findByStartDateBetween(start, end);

            if (leaves.isEmpty()) {
                return Response.error("No leaves found between given dates", 404);
            }

            List<LeaveDto> leaveDtoList = LeaveMapper.toLeaveDtoList(leaves);

            return Response.success("leave requests retrieved successfully").withData(leaveDtoList).withCount(leaveDtoList.size());
        } catch (Exception e) {
            return Response.error("Error in retrieving leave requests",500);
        }
    }
}
