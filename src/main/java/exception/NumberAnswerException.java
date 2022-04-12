package exception;

/**
 * class used for exception handling on number answers.
 * This class extends the {@code RuntimeException}
 */
public class NumberAnswerException extends RuntimeException {
    public NumberAnswerException() {
        super("Number Answer Not Allowed");
    }
}
