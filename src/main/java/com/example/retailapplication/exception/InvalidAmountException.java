package com.example.retailapplication.exception;

/**
 * Exception to handle invalid amount in request, such as insufficient user balance,
 * insufficient or empty product stock, and invalid requested quantity.
 */
public class InvalidAmountException extends RetailApplicationException {

    /**
     * Constructor with custom message
     * @param message to show error message
     */
    public InvalidAmountException(String message) {
        super(message);
    }
}
