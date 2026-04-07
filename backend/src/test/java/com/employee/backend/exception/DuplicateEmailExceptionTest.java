package com.employee.backend.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuplicateEmailExceptionTest {

    @Test
    void testDuplicateEmailException_WithMessage() {
        // Arrange & Act
        String message = "Email already in use";
        DuplicateEmailException exception = new DuplicateEmailException(message);

        // Assert
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testDuplicateEmailException_IsRuntimeException() {
        // Arrange & Act
        DuplicateEmailException exception = new DuplicateEmailException("Test message");

        // Assert
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void testDuplicateEmailException_DifferentMessages() {
        // Arrange
        String message1 = "Email validation failed";
        String message2 = "Email constraint violation";

        // Act
        DuplicateEmailException exception1 = new DuplicateEmailException(message1);
        DuplicateEmailException exception2 = new DuplicateEmailException(message2);

        // Assert
        assertEquals(message1, exception1.getMessage());
        assertEquals(message2, exception2.getMessage());
        assertNotEquals(exception1.getMessage(), exception2.getMessage());
    }
}
