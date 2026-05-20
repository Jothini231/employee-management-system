package com.ems.backend.controller;

import com.ems.backend.dto.ChangePasswordDto;
import com.ems.backend.dto.Response;
import com.ems.backend.dto.UpdateProfileDto;
import com.ems.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    private final EmployeeService employeeService;

    @Autowired
    public SettingsController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<Response> updateProfile(@PathVariable Long id, @RequestBody UpdateProfileDto updateProfileDto) {
        Response response = employeeService.updateProfile(id, updateProfileDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<Response> changePassword(@PathVariable Long id, @RequestBody ChangePasswordDto changePasswordDto) {
        Response response = employeeService.changePassword(id, changePasswordDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
