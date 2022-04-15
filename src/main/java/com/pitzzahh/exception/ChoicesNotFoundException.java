package com.pitzzahh.exception;

import java.io.FileNotFoundException;

/**
 * class used for exception handling, throws when the choices do not exist.
 * This class extends the {@code FileNotFoundException}
 */
public class ChoicesNotFoundException extends FileNotFoundException {
    public ChoicesNotFoundException() {
        super("Choices File Does Not Exist or File is Incorrect!");
    }
}
