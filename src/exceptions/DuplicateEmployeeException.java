package exceptions;

/**
 * Custom Exception thrown when an attempt is made to add an employee
 * with an ID that already exists in the system.
 */
public class DuplicateEmployeeException extends Exception {
    private static final long serialVersionUID = 1L;
    private final int duplicateId;

    public DuplicateEmployeeException(String message) {
        super(message);
        this.duplicateId = -1;
    }

    public DuplicateEmployeeException(String message, int duplicateId) {
        super(message);
        this.duplicateId = duplicateId;
    }

    public DuplicateEmployeeException(String message, Throwable cause) {
        super(message, cause);
        this.duplicateId = -1;
    }

    public int getDuplicateId() {
        return duplicateId;
    }
}
