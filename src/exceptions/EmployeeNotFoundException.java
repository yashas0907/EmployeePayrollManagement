package exceptions;

/**
 * Custom Exception thrown when an employee with a given ID cannot be found.
 */
public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
