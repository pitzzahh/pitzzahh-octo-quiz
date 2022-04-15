package com.pitzzahh.exception;

/**
 * class used for exception handling on answers that are not the same as the message given.
 * This class extends the {@code RuntimeException}
 */
public class InvalidNumberResponseException extends RuntimeException {
    public InvalidNumberResponseException(String message) {
        super(message);
    }
}
