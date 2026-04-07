package com.employee.backend.dto;

import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDate;

@Data
@Builder
@JsonPropertyOrder({"id", "firstName", "lastName", "email", "jobTitle", "role", "department", "hireDate", "isActive"})
public class EmployeeResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String jobTitle;
    private String role;
    private String department;
    private LocalDate hireDate;
    private boolean isActive;

}
