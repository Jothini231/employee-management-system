package com.ems.backend.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String email;

    private LocalDate dateOfBirth;

    private String gender;

    private String address;

    private String contactNumber;

    @Column(unique = true,nullable = false)
    private String employeeCode;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private String designation;

    private String role;

    private LocalDate dateOfJoining;

    private String reportingManager;

    private Double salary;

    private String bankAccountNumber;

    private String userName;

    private String password;

    private LocalDate lastLogin;

    private String photo;

    private String status;

}
