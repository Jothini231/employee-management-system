package com.ems.backend.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String contactNumber;

    private String employeeCode;
    private Long departmentId;
    private String designation;
    private String role;
    private LocalDate dateOfJoining;
    private String reportingManager;
    private Double salary;
    private String bankAccountNumber;
    private String userName;
    private String password;
    private String photo;
    private String status;

}
