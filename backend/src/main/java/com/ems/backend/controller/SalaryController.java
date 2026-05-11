package com.ems.backend.controller;


import com.ems.backend.dto.Response;
import com.ems.backend.dto.SalaryDto;
import com.ems.backend.service.LeaveService;
import com.ems.backend.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/salaries")
public class SalaryController {

    private SalaryService salaryService;

    @Autowired
    public SalaryController(SalaryService salaryService){
        this.salaryService = salaryService;
    }

    @PostMapping
    public ResponseEntity<Response> addSalary(@RequestBody SalaryDto salaryDto){
        Response response = salaryService.addSalary(salaryDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllSalaries(){
        Response response = salaryService.getAllSalaries();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateSalary(@PathVariable Long id , @RequestBody SalaryDto salaryDto){
        Response response = salaryService.updateSalary(id,salaryDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteSalary(@PathVariable Long id){
        Response response = salaryService.deleteSalary(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getSalaryById(@PathVariable Long id){
        Response response = salaryService.getSalaryById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
