package com.payconiq.stocks.exception;

/**
 * Exception to be thrown when a resource is not found.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor for ResourceNotFoundException.
     *
     * @param message the message to be displayed
     */
    public ResourceNotFoundException(String message) {

        super(message);
    }
}
