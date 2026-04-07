package com.employee.backend.service.impl;

import com.employee.backend.dto.EmployeeRequest;
import com.employee.backend.dto.EmployeeResponse;
import com.employee.backend.exception.DuplicateEmailException;
import com.employee.backend.exception.EmployeeNotFoundException;
import com.employee.backend.model.Employee;
import com.employee.backend.repository.EmployeeRepository;
import com.employee.backend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        log.info("Fetching all employees");
        return employeeRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        log.info("Fetching employee with id: {}", id);
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Employee with id {} not found", id);
                        return new EmployeeNotFoundException("Employee with id " + id + " not found");
                    });
        return mapToResponse(employee);
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        log.info("Creating new employee with email: {}", request.getEmail());

        employeeRepository.findByEmail(request.getEmail()).ifPresent(existing -> {
            log.warn("Duplicate email detected: {}", request.getEmail());
            throw new DuplicateEmailException("Email " + request.getEmail() + " is already in use");
        });

        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .jobTitle(request.getJobTitle())
                .role(request.getRole() != null ? request.getRole() : "reader")
                .department(request.getDepartment())
                .hireDate(request.getHireDate())
                .isActive(true)
                .build();
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee created with id: {}", savedEmployee.getId());
        return mapToResponse(savedEmployee);
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        log.info("Updating employee with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Employee with id {} not found", id);
                    return new EmployeeNotFoundException("Employee with id " + id + " not found");
                });
        employeeRepository.findByEmail(request.getEmail())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    log.warn("Duplicate email detected: {}", request.getEmail());
                    throw new DuplicateEmailException("Email " + request.getEmail() + " is already in use");
                });

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setJobTitle(request.getJobTitle());
        employee.setRole(request.getRole() != null ? request.getRole() : employee.getRole());
        employee.setDepartment(request.getDepartment());
        employee.setHireDate(request.getHireDate());
        employee.setActive(request.isActive());

        Employee updatedEmployee = employeeRepository.save(employee);
        log.info("Employee updated with id: {}", updatedEmployee.getId());
        return mapToResponse(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Employee with id {} not found", id);
                    return new EmployeeNotFoundException("Employee with id " + id + " not found");
                });
        employeeRepository.delete(employee);
        log.info("Employee deleted with id: {}", id);
    }

    @Override
    public List<EmployeeResponse> searchEmployees(String query) {
        log.info("Searching employees with query: {}", query);
        return employeeRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .jobTitle(employee.getJobTitle())
                .role(employee.getRole() != null ? employee.getRole() : "reader")
                .department(employee.getDepartment())
                .hireDate(employee.getHireDate())
                .isActive(employee.isActive())
                .build();
    }
}
