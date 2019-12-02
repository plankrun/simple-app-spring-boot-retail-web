package com.example.retailapplication.exception;

/**
 * Exception to handle empty data from database, such as empty list of transaction,
 * product, or user, and invalid login credentials
 */
public class DataNotFoundException extends RetailApplicationException {

    /**
     * Constructor with custom message
     * @param message to show error message
     */
    public DataNotFoundException(String message) {
        super(message);
    }
}
