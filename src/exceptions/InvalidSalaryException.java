package exceptions;

/**
 * Custom Exception thrown when an invalid salary amount (e.g. <= 0, NaN, Infinity) is entered.
 */
public class InvalidSalaryException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidSalaryException() {
        super("Invalid salary amount entered.");
    }

    public InvalidSalaryException(String message) {
        super(message);
    }

    public InvalidSalaryException(String message, Throwable cause) {
        super(message, cause);
    }
}
