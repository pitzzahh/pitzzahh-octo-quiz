package com.pitzzahh.exception;

import java.io.FileNotFoundException;

/**
 * class used for exception handling, throws when the questions do not exist.
 * This class extends the {@code FileNotFoundException}
 */
public class QuestionsNotFoundException extends FileNotFoundException {
    public QuestionsNotFoundException() {
        super("Questions File Does Not Exist or File is Incorrect! ");
    }
}
