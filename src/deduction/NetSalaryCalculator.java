package deduction;

/**
 * Calculates deductions and final net salary:
 * - PF = 12% of basic salary
 * - Tax = calculated from gross salary via TaxCalculator
 * - Net Salary = Gross Salary - PF - Tax
 */
public class NetSalaryCalculator {
    public static final double PF_RATE = 0.12;

    public static double calculatePF(double basicSalary) {
        if (basicSalary <= 0 || Double.isNaN(basicSalary) || Double.isInfinite(basicSalary)) {
            throw new IllegalArgumentException("Basic salary must be finite and positive for PF calculation.");
        }
        return basicSalary * PF_RATE;
    }

    public static double calculateNetSalary(double grossSalary, double totalDeductions) {
        double net = grossSalary - totalDeductions;
        return net < 0.0 ? 0.0 : net;
    }
}
