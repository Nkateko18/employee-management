package com.employee.backend.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeNotFoundExceptionTest {

    @Test
    void testEmployeeNotFoundException_WithMessage() {
        // Arrange & Act
        String message = "Employee with id 1 not found";
        EmployeeNotFoundException exception = new EmployeeNotFoundException(message);

        // Assert
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testEmployeeNotFoundException_IsRuntimeException() {
        // Arrange & Act
        EmployeeNotFoundException exception = new EmployeeNotFoundException("Test message");

        // Assert
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void testEmployeeNotFoundException_DifferentMessages() {
        // Arrange
        String message1 = "Employee not found";
        String message2 = "Invalid employee ID";

        // Act
        EmployeeNotFoundException exception1 = new EmployeeNotFoundException(message1);
        EmployeeNotFoundException exception2 = new EmployeeNotFoundException(message2);

        // Assert
        assertEquals(message1, exception1.getMessage());
        assertEquals(message2, exception2.getMessage());
        assertNotEquals(exception1.getMessage(), exception2.getMessage());
    }
}
