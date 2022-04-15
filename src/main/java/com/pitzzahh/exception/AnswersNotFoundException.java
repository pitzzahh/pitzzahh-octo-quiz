package com.pitzzahh.exception;

import java.io.FileNotFoundException;

/**
 * class used for exception handling, throws when the answers do not exist.
 * This class extends the {@code FileNotFoundException}
 */
public class AnswersNotFoundException extends FileNotFoundException {
    public AnswersNotFoundException() {
        super("Answers File Does Not Exist or File is Incorrect");
    }
}
