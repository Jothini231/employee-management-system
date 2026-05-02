package com.ems.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "departments")
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , unique = true)
    private String name;

    @Column(nullable = false,unique = true)
    private String departmentCode;

    private String description;

    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Employee> employees;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    private Boolean status = true;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Department(){}

    public Department(String name,String description,String departmentCode){
        this.name=name;
        this.description=description;
        this.departmentCode=departmentCode;
    }

    @PrePersist
    protected void onCreate(){
        createdAt=LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt=LocalDateTime.now();
    }

}
