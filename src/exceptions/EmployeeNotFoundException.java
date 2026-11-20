package exceptions;

/**
 * Custom Exception thrown when an employee with a given ID cannot be found.
 */
public class EmployeeNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    private final int searchId;

    public EmployeeNotFoundException(String message) {
        super(message);
        this.searchId = -1;
    }

    public EmployeeNotFoundException(String message, int searchId) {
        super(message);
        this.searchId = searchId;
    }

    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.searchId = -1;
    }

    public int getSearchId() {
        return searchId;
    }
}
