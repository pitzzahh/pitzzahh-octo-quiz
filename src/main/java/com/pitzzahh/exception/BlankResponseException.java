package com.pitzzahh.exception;

/**
 * class used for exception handling on blank answers.
 * This class extends the {@code RuntimeException}
 */
public class BlankResponseException extends RuntimeException {
    public BlankResponseException() {
        super("Blank Response Not Allowed");
    }
}
