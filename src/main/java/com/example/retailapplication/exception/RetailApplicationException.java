package com.example.retailapplication.exception;

/**
 * This class is an application-level exception that will be derived by other custom exceptions
 */
public class RetailApplicationException extends Exception {

    /**
     * Constructor with custom message
     * @param message to show error message
     */
    public RetailApplicationException(String message) {
        super(message);
    }
}
