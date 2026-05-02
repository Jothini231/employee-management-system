package com.ems.backend.service.impl;

import com.ems.backend.dto.DepartmentDto;
import com.ems.backend.dto.Response;
import com.ems.backend.entity.Department;
import com.ems.backend.entity.Employee;
import com.ems.backend.mapper.DepartmentMapper;
import com.ems.backend.repository.DepartmentRepository;
import com.ems.backend.repository.EmployeeRepository;
import com.ems.backend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentRepository departmentRepository;

    private EmployeeRepository employeeRepository
            ;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository,EmployeeRepository employeeRepository){
        this.departmentRepository = departmentRepository;
        this.employeeRepository=employeeRepository;
    }

    @Override
    public Response createDepartment(DepartmentDto dto) {

        try{
            if (dto == null){
                return Response.error("Department data required",400);
            }

            if (departmentRepository.existsByDepartmentCode(dto.getDepartmentCode())){
                return Response.error("Department already exists",409);
            }

            Department dept = DepartmentMapper.toEntity(dto);
            Department saved = departmentRepository.save(dept);

            return Response.success("Department created").withData(DepartmentMapper.toDto(saved));

        } catch (Exception e) {
             return Response.error("Error creating department",500);
        }

    }

    @Override
    public Response getAllDepartments() {

        try {
            List<Department> departments = departmentRepository.findAll();
            if (departments.isEmpty()) {
                return Response.error("No department found", 404);
            }

            List<DepartmentDto> departmentDtos = DepartmentMapper.toDepartmentDtoList(departments);
            return Response.success("Departments retrieved successfully").withData(departmentDtos).withCount(departmentDtos.size());
        } catch (Exception e) {
            return Response.error("Error in retrieving departments", 500);
        }
    }

    @Override
    public Response getDepartmentById(Long id) {
        try{

            Department department = departmentRepository.findById(id).orElse(null);
            if(department == null){
                return Response.error("No department found with id : " + id ,404);
            }

            DepartmentDto departmentDto = DepartmentMapper.toDto(department);
            return Response.success("Department retrieved successfully").withData(departmentDto);

        } catch (Exception e) {
            return Response.error("Error in retrieving department", 500);
        }

    }

    @Override
    public Response updateDepartment(Long id, DepartmentDto departmentDto) {

        try {
            Department existingDepartment = departmentRepository.findById(id).orElse(null);
            if (existingDepartment == null) {
                return Response.error("No department found with id: " + id, 404);
            }

            if (departmentDto.getName() != null) existingDepartment.setName(departmentDto.getName());
            if (departmentDto.getDepartmentCode() != null)
                existingDepartment.setDepartmentCode(departmentDto.getDepartmentCode());
            if (departmentDto.getDescription() != null)
                existingDepartment.setDescription(departmentDto.getDescription());
            if (departmentDto.getStatus() != null) existingDepartment.setStatus(departmentDto.getStatus());

            Department updateddepartment = departmentRepository.save(existingDepartment);

            return Response.success("Department updated successfully").withData(DepartmentMapper.toDto(updateddepartment));
        } catch (Exception e) {
            return Response.error("Error updating department", 500);
        }
    }

    @Override
    public Response deleteDepartment(Long id) {
        try {
            Department department = departmentRepository.findById(id).orElse(null);
            if (department == null) {
                return Response.error("Department not found", 404);
            }

            departmentRepository.delete(department);
            return Response.success("Department deleted successfully");
        } catch (Exception e) {
            return Response.error("Error deleting department",500);
        }
    }

    @Override
    public Response assignManager(Long deptId, Long managerId) {

        Department department = departmentRepository.findById(deptId).orElse(null);
        if(department == null){
            return Response.error("Department not found",404);
        }

        Employee manager = employeeRepository.findById(managerId).orElse(null);
        if(manager == null){
            return Response.error("Employee not found",404);
        }

        if(manager.getDepartment()==null || !manager.getDepartment().getId().equals(deptId)){
            return Response.error("Employee not in this department",404);
        }

        if (!"MANAGER".equalsIgnoreCase(manager.getRole())){
           return Response.error("Not a manager role",404);
        }
        department.setManager(manager);

        return Response.success("Manager assigned to a department").withData(DepartmentMapper.toDto(departmentRepository.save(department)));

    }


}
