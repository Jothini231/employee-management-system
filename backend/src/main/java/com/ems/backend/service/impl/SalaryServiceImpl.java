package com.ems.backend.service.impl;

import com.ems.backend.dto.Response;
import com.ems.backend.dto.SalaryDto;
import com.ems.backend.dto.SalaryResponseDto;
import com.ems.backend.entity.Employee;
import com.ems.backend.entity.Salary;
import com.ems.backend.mapper.SalaryMapper;
import com.ems.backend.repository.EmployeeRepository;
import com.ems.backend.repository.SalaryRepository;
import com.ems.backend.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SalaryServiceImpl implements SalaryService {

    private SalaryRepository salaryRepository;

    private EmployeeRepository employeeRepository;

    @Autowired
    public SalaryServiceImpl(SalaryRepository salaryRepository,EmployeeRepository employeeRepository){
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Response addSalary(SalaryDto salaryDto) {

        try {
            Employee employee = employeeRepository.findById(salaryDto.getEmployeeId()).orElse(null);

            if (employee == null) {
                return Response.error("Employee not found", 404);
            }

            Double netSalary = salaryDto.getBasicSalary() + salaryDto.getAllowance() + salaryDto.getBonus() - salaryDto.getDeduction();

            Salary salary = new Salary();
            salary.setEmployee(employee);
            salary.setBasicSalary(salaryDto.getBasicSalary());
            salary.setAllowance(salaryDto.getAllowance());
            salary.setBonus(salaryDto.getBonus());
            salary.setDeduction(salaryDto.getDeduction());
            salary.setNetSalary(netSalary);
            salary.setMonth(salaryDto.getMonth());

            Salary savedSalary = salaryRepository.save(salary);

            return Response.success("Salary added successfully").withData(SalaryMapper.toDto(savedSalary));

        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("Error in adding salary",500);
        }
    }

    @Override
    public Response getAllSalaries() {

        try {
            List<Salary> salaries = salaryRepository.findAll();
            if (salaries.isEmpty()) {
                return Response.error("No salary found", 404);
            }

            List<SalaryResponseDto> salaryDtos = SalaryMapper.toDtoList(salaries);
            return Response.success("Salaries retrieved successfully").withData(salaryDtos).withCount(salaryDtos.size());
        } catch (Exception e) {
            return Response.error("Error in retrieving salaries",500);
        }
    }

    @Override
    public Response updateSalary(Long id , SalaryDto salaryDto) {

        try {

            Salary salary = salaryRepository.findById(id).orElse(null);

            if (salary == null) {
                return Response.error("Salary not found with id : " + id, 404);
            }

            Employee employee = employeeRepository.findById(salaryDto.getEmployeeId()).orElse(null);

            if (employee == null) {
                return Response.error("Employee not found", 404);
            }

            Double netSalary = salaryDto.getBasicSalary() + salaryDto.getAllowance() + salaryDto.getBonus() - salaryDto.getDeduction();

            salary.setEmployee(employee);
            salary.setBasicSalary(salaryDto.getBasicSalary());
            salary.setAllowance(salaryDto.getAllowance());
            salary.setBonus(salaryDto.getBonus());
            salary.setDeduction(salaryDto.getDeduction());
            salary.setNetSalary(netSalary);
            salary.setMonth(salaryDto.getMonth());

            Salary savedSalary = salaryRepository.save(salary);

            return Response.success("Salary updated successfully").withData(SalaryMapper.toDto(savedSalary));

        } catch (Exception e) {
            return Response.error("Error in retrieving salaries",500);
        }
    }

    @Override
    public Response deleteSalary(Long id){

        try {
            Salary salary = salaryRepository.findById(id).orElse(null);

            if (salary == null) {
                return Response.error("Salary not found with id " + id, 404);
            }

            salaryRepository.delete(salary);
            return Response.success("Salary deleted successfully");
        } catch (Exception e) {
            return Response.error("Error in retrieving salaries",500);
        }

    }

    @Override
    public Response getSalaryById(Long id) {

        try {
            Salary salary = salaryRepository.findById(id).orElse(null);

            if (salary == null) {
                return Response.error("Salary not found with id : " + id, 404);
            }

            SalaryResponseDto salaryDto = SalaryMapper.toDto(salary);
            return Response.success("Salary retrieved successfully").withData(salaryDto);
        } catch (Exception e) {
            return Response.error("Error in retrieving salaries",500);
        }
    }

    @Override
    public Response filterSalaries(String month, Long departmentId, Long employeeId) {

        try {
            List<Salary> salaries = salaryRepository.findAll();

            if (salaries.isEmpty()) {
                return Response.error("Salaries not found", 404);
            }

            List<SalaryResponseDto> filtered = salaries.stream()
                    .filter(s ->
                            month == null || s.getMonth().equalsIgnoreCase(month))

                    .filter(s ->
                            employeeId == null || s.getEmployee().getId().equals(employeeId))

                    .filter(s ->
                            departmentId == null || s.getEmployee().getDepartment().getId().equals(departmentId))

                    .map(SalaryMapper::toDto)

                    .collect(Collectors.toList());

            return Response.success("filter applied successfully").withData(filtered).withCount(filtered.size());
        } catch (Exception e) {
            return Response.error("Error in filtering salaries",500);
        }
    }

    @Override
    public Response searchSalaries(String keyword) {

        try {
            List<Salary> salaries = salaryRepository.findAll();

            if (salaries.isEmpty()) {
                return Response.error("Salaries not found", 404);
            }

            List<SalaryResponseDto> searched = salaries.stream()
                    .filter(s ->
                            s.getEmployee().getFirstName().toLowerCase().contains(keyword.toLowerCase()))
                    .map(SalaryMapper::toDto)
                    .collect(Collectors.toList());

            return Response.success("search results fetched successfully").withData(searched).withCount(searched.size());
        } catch (Exception e) {
            return Response.error("Error in searching salaries",500);
        }
    }
}
