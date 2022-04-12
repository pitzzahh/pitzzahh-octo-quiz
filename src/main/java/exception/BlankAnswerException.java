package exception;

/**
 * class used for exception handling on blank answers.
 * This class extends the {@code RuntimeException}
 */
public class BlankAnswerException extends RuntimeException {
    public BlankAnswerException() {
        super("Blank Answer Not Allowed");
    }
}
