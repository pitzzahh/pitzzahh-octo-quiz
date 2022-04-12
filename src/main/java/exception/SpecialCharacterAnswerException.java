package exception;

/**
 * class used for exception handling on special character answers.
 * This class extends the {@code RuntimeException}
 */
public class SpecialCharacterAnswerException extends RuntimeException {
    public SpecialCharacterAnswerException() {
        super("Special Character Answer Not Allowed");
    }
}
