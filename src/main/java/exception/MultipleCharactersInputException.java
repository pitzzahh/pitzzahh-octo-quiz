package exception;

/**
 * class used for exception handling on answers that are multiple sequence of characters.
 * This class extends the {@code RuntimeException}
 */
public class MultipleCharactersInputException extends RuntimeException {
    public MultipleCharactersInputException() {
        super("Multiple Characters Response Not Allowed");
    }
}
