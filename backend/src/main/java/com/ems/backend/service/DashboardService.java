package com.ems.backend.service;

import com.ems.backend.dto.*;

import java.util.List;

public interface DashboardService {
    Response getDashboardStats();
    Response getEmployeesPerDepartment();
    Response getLeaveStatistics();
    Response getMonthlySalaryExpenses();
    Response getRecentActivities();
    Response getNotifications();
}
