package exception;

/**
 * class used for exception handling on special character answers.
 * This class extends the {@code RuntimeException}
 */
public class SpecialCharacterResponseException extends RuntimeException {
    public SpecialCharacterResponseException() {
        super("Special Character Response Not Allowed");
    }
}
