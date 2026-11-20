package exceptions;

/**
 * Custom Exception thrown when an invalid salary amount (e.g. <= 0) is entered.
 */
public class InvalidSalaryException extends Exception {
    public InvalidSalaryException(String message) {
        super(message);
    }
}
