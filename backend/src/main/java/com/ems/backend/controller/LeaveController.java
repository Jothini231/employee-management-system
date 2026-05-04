package com.ems.backend.controller;

import com.ems.backend.dto.LeaveDto;
import com.ems.backend.dto.Response;
import com.ems.backend.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private LeaveService leaveService;

    @Autowired
    public LeaveController(LeaveService leaveService){
        this.leaveService = leaveService;
    }

    @GetMapping
    public ResponseEntity<Response> getAllLeaves(){
        Response response = leaveService.getAllLeaves();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping
    public ResponseEntity<Response> applyLeave(@RequestBody LeaveDto leaveDto){
        Response response = leaveService.applyLeave(leaveDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Response> approveLeave(@PathVariable Long id){
        Response response = leaveService.approveLeave(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<Response> rejectLeave(@PathVariable Long id){
        Response response = leaveService.rejectLeave(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/status")
    public ResponseEntity<Response> getLeavesByStatus(@RequestParam String status){
        Response response = leaveService.getLeaveByStaus(status);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/employee")
    public ResponseEntity<Response> getLeavesByEmployee(@RequestParam Long id){
        Response response = leaveService.getLeaveByEmployee(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/department")
    public ResponseEntity<Response> getLeavesByDepartment(@RequestParam Long id){
        Response response = leaveService.getLeaveByDepartment(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/between-dates")
    public ResponseEntity<Response> getLeavesBetweenDates(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate) {
        Response response = leaveService.getLeaveBetweenDates(startDate, endDate);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }
}
