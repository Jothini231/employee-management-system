package com.ems.backend.service;

import com.ems.backend.dto.LeaveDto;
import com.ems.backend.dto.Response;

public interface LeaveService {

    Response getAllLeaves();

    Response applyLeave(LeaveDto leaveDto);

    Response approveLeave(Long id);

    Response rejectLeave(Long id);
}
