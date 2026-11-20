package exceptions;

/**
 * Custom Exception thrown when an attempt is made to add an employee
 * with an ID that already exists in the system.
 */
public class DuplicateEmployeeException extends Exception {
    public DuplicateEmployeeException(String message) {
        super(message);
    }
}
