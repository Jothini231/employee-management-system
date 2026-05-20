package com.ems.backend.controller;

import com.ems.backend.dto.*;
import com.ems.backend.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/dashboard")
@AllArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<Response> getDashboardStats() {
        Response response = dashboardService.getDashboardStats();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/employees-per-department")
    public ResponseEntity<Response> getEmployeesPerDepartment() {
        Response response = dashboardService.getEmployeesPerDepartment();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/leave-statistics")
    public ResponseEntity<Response> getLeaveStatistics() {
        Response response = dashboardService.getLeaveStatistics();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/monthly-salary-expenses")
    public ResponseEntity<Response> getMonthlySalaryExpenses() {
        Response response = dashboardService.getMonthlySalaryExpenses();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/recent-activities")
    public ResponseEntity<Response> getRecentActivities() {
        Response response = dashboardService.getRecentActivities();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/notifications")
    public ResponseEntity<Response> getNotifications() {
        Response response = dashboardService.getNotifications();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
