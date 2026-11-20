package deduction;

/**
 * Enumeration representing income tax slabs and rates:
 * - EXEMPT: Gross <= 50,000 : 0%
 * - MODERATE: Gross > 50,000 and <= 100,000 : 10%
 * - HIGH: Gross > 100,000 : 20%
 */
public enum TaxSlab {
    EXEMPT(0.0, 50000.0, 0.0, "Gross <= 50,000 (0% - Exempt)"),
    MODERATE(50000.0, 100000.0, 0.10, "50,000 < Gross <= 100,000 (10%)"),
    HIGH(100000.0, Double.POSITIVE_INFINITY, 0.20, "Gross > 100,000 (20%)");

    private final double lowerLimit;
    private final double upperLimit;
    private final double rate;
    private final String description;

    TaxSlab(double lowerLimit, double upperLimit, double rate, String description) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.rate = rate;
        this.description = description;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public double getUpperLimit() {
        return upperLimit;
    }

    public double getRate() {
        return rate;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Determines which tax slab applies for a given gross salary.
     *
     * @param grossSalary the gross salary to evaluate
     * @return the corresponding TaxSlab
     */
    public static TaxSlab getSlabForGrossSalary(double grossSalary) {
        DeductionUtils.validateNonNegative(grossSalary, "Gross salary");
        if (grossSalary <= EXEMPT.upperLimit) {
            return EXEMPT;
        } else if (grossSalary <= MODERATE.upperLimit) {
            return MODERATE;
        } else {
            return HIGH;
        }
    }

    @Override
    public String toString() {
        return String.format("%s [Lower: %.2f, Upper: %s, Rate: %.0f%%]",
                name(), lowerLimit, Double.isInfinite(upperLimit) ? "Unlimited" : String.format("%.2f", upperLimit), rate * 100);
    }
}
