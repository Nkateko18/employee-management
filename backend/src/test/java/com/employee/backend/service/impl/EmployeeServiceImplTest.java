package com.employee.backend.service.impl;

import com.employee.backend.dto.EmployeeRequest;
import com.employee.backend.dto.EmployeeResponse;
import com.employee.backend.exception.DuplicateEmailException;
import com.employee.backend.exception.EmployeeNotFoundException;
import com.employee.backend.model.Employee;
import com.employee.backend.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee testEmployee;
    private EmployeeRequest testRequest;

    @BeforeEach
    void setUp() {
        testEmployee = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .jobTitle("Software Engineer")
                .department("Engineering")
                .hireDate(LocalDate.of(2023, 1, 15))
                .isActive(true)
                .build();

        testRequest = new EmployeeRequest();
        testRequest.setFirstName("John");
        testRequest.setLastName("Doe");
        testRequest.setEmail("john.doe@example.com");
        testRequest.setJobTitle("Software Engineer");
        testRequest.setDepartment("Engineering");
        testRequest.setHireDate(LocalDate.of(2023, 1, 15));
        testRequest.setActive(true);
    }

    @Test
    void testGetAllEmployees() {
        // Arrange
        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .jobTitle("Product Manager")
                .department("Product")
                .hireDate(LocalDate.of(2022, 6, 1))
                .isActive(true)
                .build();

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(testEmployee, employee2));

        // Act
        List<EmployeeResponse> result = employeeService.getAllEmployees();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetAllEmployeesEmptyList() {
        // Arrange
        when(employeeRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<EmployeeResponse> result = employeeService.getAllEmployees();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetEmployeeById_Success() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(testEmployee));

        // Act
        EmployeeResponse result = employeeService.getEmployeeById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("john.doe@example.com", result.getEmail());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEmployeeById_NotFound() {
        // Arrange
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.getEmployeeById(999L);
        });
        verify(employeeRepository, times(1)).findById(999L);
    }

    @Test
    void testCreateEmployee_Success() {
        // Arrange
        Employee savedEmployee = Employee.builder()
                .id(1L)
                .firstName(testRequest.getFirstName())
                .lastName(testRequest.getLastName())
                .email(testRequest.getEmail())
                .jobTitle(testRequest.getJobTitle())
                .department(testRequest.getDepartment())
                .hireDate(testRequest.getHireDate())
                .isActive(true)
                .build();

        when(employeeRepository.findByEmail(testRequest.getEmail())).thenReturn(Optional.empty());
        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);

        // Act
        EmployeeResponse result = employeeService.createEmployee(testRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertTrue(result.isActive());
        verify(employeeRepository, times(1)).findByEmail(testRequest.getEmail());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testCreateEmployee_DuplicateEmail() {
        // Arrange
        when(employeeRepository.findByEmail(testRequest.getEmail())).thenReturn(Optional.of(testEmployee));

        // Act & Assert
        assertThrows(DuplicateEmailException.class, () -> {
            employeeService.createEmployee(testRequest);
        });
        verify(employeeRepository, times(1)).findByEmail(testRequest.getEmail());
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_Success() {
        // Arrange
        Employee updatedEmployee = Employee.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .jobTitle("Senior Software Engineer")
                .department("Engineering")
                .hireDate(LocalDate.of(2023, 1, 15))
                .isActive(true)
                .build();

        EmployeeRequest updateRequest = new EmployeeRequest();
        updateRequest.setFirstName("Jane");
        updateRequest.setLastName("Doe");
        updateRequest.setEmail("jane.doe@example.com");
        updateRequest.setJobTitle("Senior Software Engineer");
        updateRequest.setDepartment("Engineering");
        updateRequest.setHireDate(LocalDate.of(2023, 1, 15));
        updateRequest.setActive(true);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(testEmployee));
        when(employeeRepository.findByEmail("jane.doe@example.com")).thenReturn(Optional.empty());
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        // Act
        EmployeeResponse result = employeeService.updateEmployee(1L, updateRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Jane", result.getFirstName());
        assertEquals("jane.doe@example.com", result.getEmail());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_EmployeeNotFound() {
        // Arrange
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.updateEmployee(999L, testRequest);
        });
        verify(employeeRepository, times(1)).findById(999L);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_DuplicateEmail() {
        // Arrange
        Employee otherEmployee = Employee.builder()
                .id(2L)
                .email("jane.doe@example.com")
                .build();

        EmployeeRequest updateRequest = new EmployeeRequest();
        updateRequest.setFirstName("John");
        updateRequest.setLastName("Doe");
        updateRequest.setEmail("jane.doe@example.com");
        updateRequest.setJobTitle("Software Engineer");
        updateRequest.setDepartment("Engineering");
        updateRequest.setHireDate(LocalDate.of(2023, 1, 15));
        updateRequest.setActive(true);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(testEmployee));
        when(employeeRepository.findByEmail("jane.doe@example.com")).thenReturn(Optional.of(otherEmployee));

        // Act & Assert
        assertThrows(DuplicateEmailException.class, () -> {
            employeeService.updateEmployee(1L, updateRequest);
        });
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void testDeleteEmployee_Success() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(testEmployee));

        // Act
        employeeService.deleteEmployee(1L);

        // Assert
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).delete(testEmployee);
    }

    @Test
    void testDeleteEmployee_NotFound() {
        // Arrange
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.deleteEmployee(999L);
        });
        verify(employeeRepository, times(1)).findById(999L);
        verify(employeeRepository, never()).delete(any(Employee.class));
    }

    @Test
    void testSearchEmployees_Success() {
        // Arrange
        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("James")
                .lastName("Johnson")
                .email("james.johnson@example.com")
                .jobTitle("Analyst")
                .department("Engineering")
                .hireDate(LocalDate.of(2023, 6, 1))
                .isActive(true)
                .build();

        when(employeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("John", "John"))
                .thenReturn(Arrays.asList(testEmployee, employee2));

        // Act
        List<EmployeeResponse> result = employeeService.searchEmployees("John");

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(employeeRepository, times(1))
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("John", "John");
    }

    @Test
    void testSearchEmployees_EmptyResult() {
        // Arrange
        when(employeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("Unknown", "Unknown"))
                .thenReturn(Arrays.asList());

        // Act
        List<EmployeeResponse> result = employeeService.searchEmployees("Unknown");

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(employeeRepository, times(1))
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("Unknown", "Unknown");
    }

    @Test
    void testSearchEmployees_CaseInsensitive() {
        // Arrange
        when(employeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("john", "john"))
                .thenReturn(Arrays.asList(testEmployee));

        // Act
        List<EmployeeResponse> result = employeeService.searchEmployees("john");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }
}
