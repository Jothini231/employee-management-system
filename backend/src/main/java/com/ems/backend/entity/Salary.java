package com.ems.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "salaries")
@Getter
@Setter
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

    private Double basicSalary;

    private Double allowance;

    private Double bonus;

    private Double deduction;

    private Double netSalary;

    private String month;
}
