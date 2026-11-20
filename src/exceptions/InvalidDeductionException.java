package exceptions;

/**
 * Custom Exception thrown when an invalid deduction amount (e.g. negative PF,
 * negative tax, NaN, or non-finite values) is encountered.
 *
 * Extends IllegalArgumentException so that existing method contracts in the
 * domain model remain backward compatible while enforcing strict validation.
 */
public class InvalidDeductionException extends IllegalArgumentException {
    private static final long serialVersionUID = 1L;
    private final double invalidValue;

    public InvalidDeductionException(String message) {
        super(message);
        this.invalidValue = 0.0;
    }

    public InvalidDeductionException(String message, double invalidValue) {
        super(message);
        this.invalidValue = invalidValue;
    }

    public InvalidDeductionException(String message, Throwable cause) {
        super(message, cause);
        this.invalidValue = 0.0;
    }

    public double getInvalidValue() {
        return invalidValue;
    }
}
