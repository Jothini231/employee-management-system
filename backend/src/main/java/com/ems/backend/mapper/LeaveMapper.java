package com.ems.backend.mapper;

import com.ems.backend.dto.LeaveDto;
import com.ems.backend.entity.Leave;

import java.util.List;
import java.util.stream.Collectors;

public class LeaveMapper {

    public static LeaveDto toDto(Leave leave){
        if(leave == null){
            return null;
        }

        LeaveDto dto = new LeaveDto();
        dto.setId(leave.getId());
        dto.setLeaveType(leave.getLeaveType());
        dto.setReason(leave.getReason());
        dto.setStartDate(leave.getStartDate());
        dto.setEndDate(leave.getEndDate());
        dto.setStatus(leave.getStatus());
        dto.setTotalDays(leave.getTotalDays());
        dto.setEmployeeId(leave.getEmployee().getId());
        return dto;
    }

    public static List<LeaveDto> toLeaveDtoList(List<Leave> leaves){
        return leaves.stream()
                .map(LeaveMapper::toDto)
                .collect(Collectors.toList());
    }
}
