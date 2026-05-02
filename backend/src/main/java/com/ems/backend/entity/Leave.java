package com.ems.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "leaves")
@Getter
@Setter
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String leaveType;

    private LocalDate startDate;

    private LocalDate endDate;

    private int totalDays;

    private String reason;

    private String status;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
