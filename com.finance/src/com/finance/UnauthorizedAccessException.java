package com.finance;

// Custom Checked Exception to handle Access Violations cleanly
public class UnauthorizedAccessException extends Exception {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}