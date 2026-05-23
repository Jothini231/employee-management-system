package com.ems.backend.controller;

import com.ems.backend.dto.EmployeeDto;
import com.ems.backend.service.EmployeeService;
import com.ems.backend.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/employees")
@PreAuthorize("hasRole('ADMIN')")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Response>  createEmployee(@RequestBody EmployeeDto employeeDto){
        Response response = employeeService.createEmployee(employeeDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllEmployees() {
      Response response = employeeService.getAllEmployees();
      return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<Response> getEmployeeById(@PathVariable Long id){
        Response response = employeeService.getEmployeeById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateEmployee(@PathVariable Long id,@RequestBody EmployeeDto employeeDto){
        Response response = employeeService.updateEmployee(id,employeeDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteEmployee(@PathVariable Long id){
        Response response = employeeService.deleteEmployee(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/department/{deptId}")
    public ResponseEntity<Response> getEmployeesByDepartmentId(@PathVariable Long deptId){
        Response response = employeeService.getEmployeesByDepartmentId(deptId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
