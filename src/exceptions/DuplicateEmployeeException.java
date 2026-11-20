package exceptions;

/**
 * Custom Exception thrown when an attempt is made to add an employee
 * with an ID that already exists in the system.
 */
public class DuplicateEmployeeException extends Exception {
    private static final long serialVersionUID = 1L;

    public DuplicateEmployeeException() {
        super("Employee with this ID already exists.");
    }

    public DuplicateEmployeeException(String message) {
        super(message);
    }

    public DuplicateEmployeeException(String message, Throwable cause) {
        super(message, cause);
    }
}
