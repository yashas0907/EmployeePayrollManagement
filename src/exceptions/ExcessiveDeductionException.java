package exceptions;

/**
 * Custom Exception thrown when total deductions exceed gross salary,
 * which would result in an invalid negative net salary.
 */
public class ExcessiveDeductionException extends InvalidDeductionException {
    private static final long serialVersionUID = 1L;
    private final double grossSalary;
    private final double totalDeductions;
    private final double shortfall;

    public ExcessiveDeductionException(double grossSalary, double totalDeductions) {
        super(String.format("Total deductions (Rs. %.2f) exceed gross salary (Rs. %.2f) by Rs. %.2f.",
                totalDeductions, grossSalary, (totalDeductions - grossSalary)));
        this.grossSalary = grossSalary;
        this.totalDeductions = totalDeductions;
        this.shortfall = totalDeductions - grossSalary;
    }

    public ExcessiveDeductionException(String message, double grossSalary, double totalDeductions) {
        super(message);
        this.grossSalary = grossSalary;
        this.totalDeductions = totalDeductions;
        this.shortfall = totalDeductions - grossSalary;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public double getTotalDeductions() {
        return totalDeductions;
    }

    public double getShortfall() {
        return shortfall;
    }
}
