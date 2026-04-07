package com.employee.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String jobTitle;

    @Column(nullable = true)
    @Builder.Default
    private String role = "reader";

    @Column(nullable = false)
    private String department;

    private LocalDate hireDate;

    @Column(nullable = false)
    @Builder.Default
    private boolean isActive = true;

}
