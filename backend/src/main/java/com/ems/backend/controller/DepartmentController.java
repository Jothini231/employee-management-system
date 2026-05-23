package com.ems.backend.controller;

import com.ems.backend.dto.DepartmentDto;
import com.ems.backend.dto.Response;
import com.ems.backend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/departments")
@PreAuthorize("hasRole('ADMIN')")
public class DepartmentController {

    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService){
        this.departmentService=departmentService;
    }

    @PostMapping
    public ResponseEntity<Response> createDepartment(@RequestBody DepartmentDto departmentDto){
        Response response = departmentService.createDepartment(departmentDto);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<Response> getAllDepartments(){
        Response response = departmentService.getAllDepartments();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<Response> getDepartmentById(@PathVariable Long id){
        Response response = departmentService.getDepartmentById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateDepartment(@PathVariable Long id,@RequestBody DepartmentDto departmentDto){
        Response response = departmentService.updateDepartment(id,departmentDto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteDepartment(@PathVariable Long id){
        Response response = departmentService.deleteDepartment(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{deptId}/manager/{managerId}")
    public ResponseEntity<Response> assignManager(@PathVariable Long deptId,@PathVariable Long managerId ){
        Response response = departmentService.assignManager(deptId,managerId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
