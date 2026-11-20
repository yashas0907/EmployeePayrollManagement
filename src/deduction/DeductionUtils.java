package deduction;

import exceptions.InvalidDeductionException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class providing common validation and financial rounding methods
 * for the deduction and payroll modules.
 */
public final class DeductionUtils {

    public static final int CURRENCY_DECIMAL_PLACES = 2;

    private DeductionUtils() {
        // Prevent instantiation of utility class
    }

    /**
     * Rounds a monetary figure to 2 decimal places using HALF_UP rounding (standard financial rounding).
     *
     * @param value monetary amount to round
     * @return rounded value
     * @throws InvalidDeductionException if value is NaN or Infinite
     */
    public static double round(double value) {
        validateFinite(value, "Monetary amount");
        return BigDecimal.valueOf(value)
                .setScale(CURRENCY_DECIMAL_PLACES, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Validates that a numeric monetary value is finite (neither NaN nor infinite).
     *
     * @param value the numeric value to check
     * @param fieldName descriptive name of the field for error messages
     * @throws InvalidDeductionException if value is NaN or Infinite
     */
    public static void validateFinite(double value, String fieldName) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new InvalidDeductionException(
                    fieldName + " must be a finite number. Provided: " + value, value);
        }
    }

    /**
     * Validates that a monetary value is finite and non-negative (>= 0).
     *
     * @param value monetary value to check
     * @param fieldName descriptive name of the field for error messages
     * @throws InvalidDeductionException if value is negative, NaN, or Infinite
     */
    public static void validateNonNegative(double value, String fieldName) {
        validateFinite(value, fieldName);
        if (value < 0.0) {
            throw new InvalidDeductionException(
                    fieldName + " cannot be negative. Provided: " + value, value);
        }
    }

    /**
     * Validates that a percentage rate is finite and within the range [0.0, 1.0].
     *
     * @param rate rate between 0.0 and 1.0 (e.g. 0.12 for 12%)
     * @param rateName descriptive name of the rate for error messages
     * @throws InvalidDeductionException if rate is out of bounds [0.0, 1.0]
     */
    public static void validateRate(double rate, String rateName) {
        validateFinite(rate, rateName);
        if (rate < 0.0 || rate > 1.0) {
            throw new InvalidDeductionException(
                    rateName + " must be between 0.0 (0%) and 1.0 (100%). Provided: " + rate, rate);
        }
    }
}
