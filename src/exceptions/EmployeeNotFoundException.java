package exceptions;

/**
 * Custom Exception thrown when an employee with a given ID cannot be found.
 */
public class EmployeeNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public EmployeeNotFoundException() {
        super("Employee not found.");
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }

    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
