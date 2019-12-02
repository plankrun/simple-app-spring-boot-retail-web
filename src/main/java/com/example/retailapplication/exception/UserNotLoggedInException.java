package com.example.retailapplication.exception;

/**
 * Exception to handle invalid user login status when checking
 * out a transaction
 */
public class UserNotLoggedInException extends RetailApplicationException {

    /**
     * Constructor with custom message
     * @param message to show error message
     */
    public UserNotLoggedInException(String message) {
        super(message);
    }
}
