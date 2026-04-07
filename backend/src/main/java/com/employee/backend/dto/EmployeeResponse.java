package com.employee.backend.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class EmployeeResponse {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String jobTitle;
    private String department;
    private LocalDate hireDate;
    private boolean isActive;

}
