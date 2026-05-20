package com.ems.backend.service.impl;

import com.ems.backend.dto.*;
import com.ems.backend.entity.Employee;
import com.ems.backend.entity.Leave;
import com.ems.backend.repository.DepartmentRepository;
import com.ems.backend.repository.EmployeeRepository;
import com.ems.backend.repository.LeaveRepository;
import com.ems.backend.repository.SalaryRepository;
import com.ems.backend.service.DashboardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final LeaveRepository leaveRepository;
    private final SalaryRepository salaryRepository;

    @Override
    public Response getDashboardStats() {

        try {
            DashboardStatsDto dashboardStatsDto = new DashboardStatsDto();

            dashboardStatsDto.setTotalEmployees(employeeRepository.count());
            dashboardStatsDto.setTotalDepartments(departmentRepository.count());

            dashboardStatsDto.setEmployeesOnLeave(leaveRepository.countByStatusIgnoreCase("Approved"));


            String currentMonth = YearMonth.now().toString();
            Double monthlySalaryExpense = salaryRepository.sumNetSalaryByMonth(currentMonth);
            if (monthlySalaryExpense == null) {
                monthlySalaryExpense = 0.0;
            }

            dashboardStatsDto.setMonthlySalaryExpense(monthlySalaryExpense);

            return Response.success("Dashboard stats fetched successfully").withData(dashboardStatsDto);

        } catch (Exception e) {
            return Response.error("Error in fetching dashboard stats",500);
        }
    }

    @Override
    public Response getEmployeesPerDepartment() {
        try {
            List<EmployeePerDepartmentDto> data = employeeRepository.countEmployeesPerDepartment();
            return Response.success("Employees per department fetched successfully").withData(data).withCount(data.size());
        } catch (Exception e) {
            return Response.error("Error in fetching employees",500);
        }
    }

    @Override
    public Response getLeaveStatistics() {

        try {
            long approved = leaveRepository.countByStatusIgnoreCase("Approved");
            long pending = leaveRepository.countByStatusIgnoreCase("Pending");
            long rejected = leaveRepository.countByStatusIgnoreCase("Rejected");

            List<LeaveStatisticsDto> stats = new ArrayList<>();

            stats.add(new LeaveStatisticsDto("Approved", approved));
            stats.add(new LeaveStatisticsDto("Pending", pending));
            stats.add(new LeaveStatisticsDto("Rejected", rejected));

            return Response
                    .success("Leave statistics fetched successfully")
                    .withCount(stats.size())
                    .withData(stats);
        } catch (Exception e) {
            return Response.error("Error in fetching leave statistics",500);
        }
    }

    @Override
    public Response getMonthlySalaryExpenses() {
        try {
            List<MonthlySalaryExpenseDto> data = salaryRepository.calculateMonthlySalaryExpenses();
            return Response.success("Monthly salary expenses fetched successfully").withData(data).withCount(data.size());
        } catch (Exception e) {
            return Response.error("Error in fetching monthly salary expense",500);
        }
    }

    @Override
    public Response getRecentActivities() {

        try {
            List<ActivityDto> activities = new ArrayList<>();

            List<Employee> recentEmployees = employeeRepository.findTop5ByOrderByIdDesc();
            for (Employee e : recentEmployees) {
                String date = e.getDateOfJoining() != null ? e.getDateOfJoining().toString() : LocalDate.now().toString();
                activities.add(new ActivityDto("New Employee Added", e.getFirstName() + " " + e.getLastName(), date, "Success"));
            }

            // Fetch recent leaves
            List<Leave> recentLeaves = leaveRepository.findTop5ByOrderByIdDesc();
            for (Leave l : recentLeaves) {
                String name = l.getEmployee() != null ? l.getEmployee().getFirstName() + " " + l.getEmployee().getLastName() : "Unknown";
                String date = l.getStartDate() != null ? l.getStartDate().toString() : LocalDate.now().toString();
                activities.add(new ActivityDto("Leave Submitted", name, date, l.getStatus()));
            }

            // Sort them basically by date descending (rough sort since it's a mix)
            activities.sort((a, b) -> b.getDate().compareTo(a.getDate()));

            List<ActivityDto> latestActivities = activities.size() > 5 ? activities.subList(0, 5) : activities;

            return Response.success("Recent activities fetched successfully").withCount(latestActivities.size()).withData(latestActivities);

        } catch (Exception e) {
            return Response.error("Error in fetching recent activities",500);
        }
    }

    @Override
    public Response getNotifications() {

        try {
            List<NotificationDto> notifications = new ArrayList<>();

            List<Leave> recentLeaves = leaveRepository.findTop5ByOrderByIdDesc();
            for (Leave l : recentLeaves) {
                if ("Pending".equalsIgnoreCase(l.getStatus())) {
                    String name = l.getEmployee() != null ? l.getEmployee().getFirstName() + " " + l.getEmployee().getLastName() : "Unknown";
                    notifications.add(new NotificationDto("Pending Leave Approval", name + " has requested leave.", l.getStartDate().toString(), "leave"));
                }
            }

            List<Employee> recentEmployees = employeeRepository.findTop5ByOrderByIdDesc();
            for (Employee e : recentEmployees) {
                String date = e.getDateOfJoining() != null ? e.getDateOfJoining().toString() : LocalDate.now().toString();
                notifications.add(new NotificationDto("New Employee", e.getFirstName() + " " + e.getLastName() + " joined recently.", date, "employee"));
            }

            return Response.success("Notifications fetched successfully").withData(notifications).withCount(notifications.size());
        } catch (Exception e) {
            return  Response.error("Error in fetching data",500);
        }
    }
}

