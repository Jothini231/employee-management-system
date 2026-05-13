package com.ems.backend.service;

import com.ems.backend.dto.Response;
import com.ems.backend.dto.SalaryDto;

public interface SalaryService {

    Response addSalary(SalaryDto salaryDto);

    Response getAllSalaries();

    Response updateSalary(Long id , SalaryDto salaryDto);

    Response deleteSalary(Long id);

    Response getSalaryById(Long id);

    Response filterSalaries(String month , Long departmentId , Long employeeId );

    Response searchSalaries(String keyword);

}
