package com.ems.backend.service.impl;

import com.ems.backend.dto.ChangePasswordDto;
import com.ems.backend.dto.Response;
import com.ems.backend.dto.EmployeeDto;
import com.ems.backend.dto.UpdateProfileDto;
import com.ems.backend.entity.Department;
import com.ems.backend.entity.Employee;
import com.ems.backend.mapper.EmployeeMapper;
import com.ems.backend.repository.DepartmentRepository;
import com.ems.backend.repository.EmployeeRepository;
import com.ems.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {


    private EmployeeRepository employeeRepository;

    private DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository=departmentRepository;
    }

    @Override
    public Response getAllEmployees() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            List<EmployeeDto> employeeDtos = EmployeeMapper.toEmployeeDtoList(employees);
            return Response.success("Employees retrieved successfully").withData(employeeDtos).withCount(employeeDtos.size());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("Error in retrieving employees", 500);
        }
    }


    @Override
    public Response getEmployeeById(Long id) {
        try {
            Employee employee = employeeRepository.findById(id).orElse(null);
            if (employee == null) {
                return Response.error("No employee found with id : " + id, 404);
            }

            EmployeeDto employeeDto = EmployeeMapper.toDto(employee);
            return Response.success("Employee retrieved successfully").withData(employeeDto);
        } catch (Exception e) {
            return Response.error("Error with  retrieving employee", 500);
        }
    }

    @Override
    public Response createEmployee(EmployeeDto employeeDto) {

        try {
            if (employeeDto == null) {
                return Response.error("Employee details required", 400);
            }

            if (employeeRepository.existsByEmployeeCode(employeeDto.getEmployeeCode())) {
                return Response.error("Employee with code " + employeeDto.getEmployeeCode() + " already exists", 409);
            }

            Employee employee = EmployeeMapper.toEntity(employeeDto);


            if (employeeDto.getDepartmentId() != null) {
                Department dept = departmentRepository.findById(employeeDto.getDepartmentId())
                        .orElse(null);

                if (dept == null) {
                    return Response.error("Department not found with id: " + employeeDto.getDepartmentId(), 404);
                }

                employee.setDepartment(dept);
            }

            Employee savedEmployee = employeeRepository.save(employee);

            return Response.success("Employee created successfully")
                    .withData(EmployeeMapper.toDto(savedEmployee));

        } catch (Exception e) {
            return Response.error("Error with creating employee", 500);
        }
    }


    @Override
    public Response updateEmployee(Long id, EmployeeDto employeeDto) {
        try {

            Employee existingEmployee = employeeRepository.findById(id).orElse(null);
            if (existingEmployee == null) {
                return Response.error("No employee found with id: " + id, 404);
            }

            if (employeeDto.getFirstName() != null) existingEmployee.setFirstName(employeeDto.getFirstName());
            if (employeeDto.getLastName() != null) existingEmployee.setLastName(employeeDto.getLastName());
            if (employeeDto.getEmail() != null) existingEmployee.setEmail(employeeDto.getEmail());
            if (employeeDto.getAddress() != null) existingEmployee.setAddress(employeeDto.getAddress());
            if (employeeDto.getContactNumber() != null) existingEmployee.setContactNumber(employeeDto.getContactNumber());
            if (employeeDto.getEmployeeCode() != null) existingEmployee.setEmployeeCode(employeeDto.getEmployeeCode());
            if (employeeDto.getDesignation() != null) existingEmployee.setDesignation(employeeDto.getDesignation());
            if (employeeDto.getRole() != null) existingEmployee.setRole(employeeDto.getRole());
            if (employeeDto.getDateOfJoining() != null) existingEmployee.setDateOfJoining(employeeDto.getDateOfJoining());
            if (employeeDto.getReportingManager() != null) existingEmployee.setReportingManager(employeeDto.getReportingManager());
            if (employeeDto.getSalary() != null) existingEmployee.setSalary(employeeDto.getSalary());
            if (employeeDto.getBankAccountNumber() != null) existingEmployee.setBankAccountNumber(employeeDto.getBankAccountNumber());
            if (employeeDto.getUserName() != null) existingEmployee.setUserName(employeeDto.getUserName());
            if (employeeDto.getPassword() != null) existingEmployee.setPassword(employeeDto.getPassword());
            if (employeeDto.getPhoto() != null) existingEmployee.setPhoto(employeeDto.getPhoto());
            if (employeeDto.getStatus() != null) existingEmployee.setStatus(employeeDto.getStatus());

            if (employeeDto.getDepartmentId() != null) {
                Department dept = departmentRepository.findById(employeeDto.getDepartmentId())
                        .orElse(null);

                if (dept == null) {
                    return Response.error("Department not found with id: " + employeeDto.getDepartmentId(), 404);
                }

                existingEmployee.setDepartment(dept);
            }

            Employee updatedEmployee = employeeRepository.save(existingEmployee);

            EmployeeDto updatedDto = EmployeeMapper.toDto(updatedEmployee);

            return Response.success("Employee updated successfully").withData(updatedDto);

        } catch (Exception e) {
            return Response.error("Error updating employee", 500);
        }
    }

    @Override
    public Response deleteEmployee(Long id) {
        try {
            Employee employee = employeeRepository.findById(id).orElse(null);
            if (employee == null) {
                return Response.error("Employee not found ", 404);
            }

            employeeRepository.delete(employee);
            return Response.success("Employee deleted successfully");
        } catch (Exception e) {
            return Response.error("Error deleting employee", 500);
        }
    }

    @Override
    public Response getEmployeesByDepartmentId(Long deptId) {
        try {

            Department department = departmentRepository.findById(deptId)
                    .orElse(null);

            if (department == null) {
                return Response.error("Department not found with id: " + deptId, 404);
            }


            List<Employee> employees = employeeRepository.findByDepartmentId(deptId);


            List<EmployeeDto> employeeDtos =
                    EmployeeMapper.toEmployeeDtoList(employees);


            return Response.success("Employees retrieved successfully")
                    .withData(employeeDtos)
                    .withCount(employeeDtos.size());

        } catch (Exception e) {
            return Response.error("Error retrieving employees by department", 500);
        }
    }

    @Override
    public Response updateProfile(Long id, UpdateProfileDto updateProfileDto) {
        try {
            Employee existingEmployee = employeeRepository.findById(id).orElse(null);
            if (existingEmployee == null) {
                return Response.error("No employee found with id: " + id, 404);
            }
            if (updateProfileDto.getFirstName() != null) existingEmployee.setFirstName(updateProfileDto.getFirstName());
            if (updateProfileDto.getLastName() != null) existingEmployee.setLastName(updateProfileDto.getLastName());
            if (updateProfileDto.getEmail() != null) existingEmployee.setEmail(updateProfileDto.getEmail());
            if (updateProfileDto.getContactNumber() != null) existingEmployee.setContactNumber(updateProfileDto.getContactNumber());
            if (updateProfileDto.getDesignation() != null) existingEmployee.setDesignation(updateProfileDto.getDesignation());
            if (updateProfileDto.getPhoto() != null) existingEmployee.setPhoto(updateProfileDto.getPhoto());

            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            return Response.success("Profile updated successfully").withData(EmployeeMapper.toDto(updatedEmployee));
        } catch (Exception e) {
            return Response.error("Error updating profile", 500);
        }
    }

    @Override
    public Response changePassword(Long id, ChangePasswordDto changePasswordDto) {
        try {
            Employee existingEmployee = employeeRepository.findById(id).orElse(null);
            if (existingEmployee == null) {
                return Response.error("No employee found with id: " + id, 404);
            }
            
            if (existingEmployee.getPassword() != null && !existingEmployee.getPassword().equals(changePasswordDto.getCurrentPassword())) {
                return Response.error("Incorrect current password", 400);
            }
            
            if (changePasswordDto.getNewPassword() == null || !changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())) {
                return Response.error("New password and confirm password do not match", 400);
            }

            existingEmployee.setPassword(changePasswordDto.getNewPassword());
            employeeRepository.save(existingEmployee);
            return Response.success("Password changed successfully");
        } catch (Exception e) {
            return Response.error("Error changing password", 500);
        }
    }
}
