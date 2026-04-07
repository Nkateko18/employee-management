package com.employee.backend.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeRequestValidationTest {

    @Autowired
    private Validator validator;

    private EmployeeRequest validRequest;

    @BeforeEach
    void setUp() {
        validRequest = new EmployeeRequest();
        validRequest.setFirstName("John");
        validRequest.setLastName("Doe");
        validRequest.setEmail("john.doe@example.com");
        validRequest.setJobTitle("Software Engineer");
        validRequest.setDepartment("Engineering");
        validRequest.setHireDate(LocalDate.of(2023, 1, 15));
        validRequest.setActive(true);
    }

    @Test
    void testValidEmployeeRequest() {
        // Act
        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testFirstNameRequired() {
        // Arrange
        validRequest.setFirstName(null);

        // Act
        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("firstName")));
    }

    @Test
    void testFirstNameBlank() {
        // Arrange
        validRequest.setFirstName("   ");

        // Act
        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("firstName")));
    }

    @Test
    void testFirstNameTooShort() {
        // Arrange
        validRequest.setFirstName("J");

        // Act
        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("firstName") &&
                        v.getMessage().contains("between 2 and 50")));
    }

    @Test
    void testFirstNameTooLong() {
        // Arrange
        validRequest.setFirstName("a".repeat(51));

        // Act
        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("firstName")));
    }

    @Test
    void testLastNameRequired() {
        // Arrange
        validRequest.setLastName(null);

        // Act
        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("lastName")));
    }

    @Test
    void testLastNameBlank() {
        // Arrange
        validRequest.setLastName("");

        // Act
        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void testEmailRequired() {
        // Arrange
        validRequest.setEmail(null);

        // Act
        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void testEmailBlank() {
        // Arrange
        validRequest.setEmail("");

        // Act
        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void testEmailInvalid() {
        // Arrange
        validRequest.setEmail("invalid-email");

        // Act
        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email") &&
                        v.getMessage().contains("valid")));
    }

    @Test
    void testJobTitleRequired() {
        // Arrange
        validRequest.setJobTitle(null);

        // Act
        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("jobTitle")));
    }

    @Test
    void testJobTitleBlank() {
        // Arrange
        validRequest.setJobTitle("");

        // Act
        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void testDepartmentRequired() {
        // Arrange
        validRequest.setDepartment(null);

        // Act
        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("department")));
    }

    @Test
    void testDepartmentBlank() {
        // Arrange
        validRequest.setDepartment("");

        // Act
        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void testHireDateRequired() {
        // Arrange
        validRequest.setHireDate(null);

        // Act
        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("hireDate")));
    }

    @Test
    void testHireDateInFuture() {
        // Arrange
        validRequest.setHireDate(LocalDate.now().plusDays(1));

        // Act
        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("hireDate") &&
                        v.getMessage().contains("cannot be in the future")));
    }

    @Test
    void testHireDateToday() {
        // Arrange
        validRequest.setHireDate(LocalDate.now());

        // Act
        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testHireDateInPast() {
        // Arrange
        validRequest.setHireDate(LocalDate.of(2000, 1, 1));

        // Act
        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testIsActiveDefaultValue() {
        // Arrange
        EmployeeRequest newRequest = new EmployeeRequest();

        // Act & Assert
        assertTrue(newRequest.isActive());
    }

    @Test
    void testValidEmailFormats() {
        // Test various valid email formats
        String[] validEmails = {
                "user@example.com",
                "user.name@example.co.uk",
                "user+tag@example.com",
                "user_name@example.com"
        };

        for (String email : validEmails) {
            validRequest.setEmail(email);
            Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);
            assertTrue(violations.isEmpty(), "Email should be valid: " + email);
        }
    }

    @Test
    void testInvalidEmailFormats() {
        // Test various invalid email formats
        String[] invalidEmails = {
                "userexample.com",
                "user@.com",
                "@example.com",
                "user @example.com"
        };

        for (String email : invalidEmails) {
            validRequest.setEmail(email);
            Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(validRequest);
            assertFalse(violations.isEmpty(), "Email should be invalid: " + email);
        }
    }
}
