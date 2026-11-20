package exceptions;

/**
 * Custom Exception thrown when an invalid salary amount (e.g. <= 0, NaN, or infinite) is entered.
 */
public class InvalidSalaryException extends Exception {
    private static final long serialVersionUID = 1L;
    private final double invalidSalary;

    public InvalidSalaryException(String message) {
        super(message);
        this.invalidSalary = 0.0;
    }

    public InvalidSalaryException(String message, double invalidSalary) {
        super(message);
        this.invalidSalary = invalidSalary;
    }

    public InvalidSalaryException(String message, Throwable cause) {
        super(message, cause);
        this.invalidSalary = 0.0;
    }

    public double getInvalidSalary() {
        return invalidSalary;
    }
}
