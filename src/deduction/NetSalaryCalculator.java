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
        return basicSalary * PF_RATE;
    }

    public static double calculateNetSalary(double grossSalary, double totalDeductions) {
        return grossSalary - totalDeductions;
    }
}
