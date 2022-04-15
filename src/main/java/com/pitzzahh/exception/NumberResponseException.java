package com.pitzzahh.exception;

/**
 * class used for exception handling on number answers.
 * This class extends the {@code RuntimeException}
 */
public class NumberResponseException extends RuntimeException {
    public NumberResponseException() {
        super("Number Response Not Allowed");
    }
}
