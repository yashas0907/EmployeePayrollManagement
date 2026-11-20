package deduction;

import exceptions.ExcessiveDeductionException;

/**
 * Calculates deductions and final net salary:
 * - PF = 12% of basic salary (with support for EPFO statutory ceiling)
 * - Tax = calculated from gross salary via TaxCalculator
 * - Net Salary = Gross Salary - Total Deductions (PF + Tax)
 *
 * Provides:
 * 1. Statutory PF ceiling computation option (EPFO standard ₹15,000 wage ceiling)
 * 2. Strict input validation against negative, NaN, and Infinite values
 * 3. Excessive deduction detection with domain-specific ExcessiveDeductionException
 * 4. High-precision financial rounding (2 decimal places)
 */
public class NetSalaryCalculator {

    public static final double PF_RATE = 0.12;
    public static final double STATUTORY_PF_WAGE_CEILING = 15000.0;
    public static final double MAX_STATUTORY_PF = STATUTORY_PF_WAGE_CEILING * PF_RATE; // 1,800.00

    /**
     * Calculates PF on basic salary using the standard 12% rate.
     * Preserves exact compatibility with project rules and README specifications:
     * PF = 12% of basic salary.
     *
     * @param basicSalary basic salary of the employee
     * @return rounded PF deduction amount
     * @throws exceptions.InvalidDeductionException if basicSalary is negative, NaN, or Infinite
     */
    public static double calculatePF(double basicSalary) {
        return calculatePF(basicSalary, false);
    }

    /**
     * Calculates PF on basic salary with an option to apply the EPFO statutory wage ceiling.
     * When applyCeiling is true, PF is computed on min(basicSalary, 15000.0).
     *
     * @param basicSalary basic salary of the employee
     * @param applyCeiling whether to cap eligible basic salary at statutory ceiling (Rs. 15,000)
     * @return rounded PF deduction amount
     * @throws exceptions.InvalidDeductionException if basicSalary is negative, NaN, or Infinite
     */
    public static double calculatePF(double basicSalary, boolean applyCeiling) {
        DeductionUtils.validateNonNegative(basicSalary, "Basic salary");

        double eligibleSalary = (applyCeiling && basicSalary > STATUTORY_PF_WAGE_CEILING)
                ? STATUTORY_PF_WAGE_CEILING
                : basicSalary;

        return DeductionUtils.round(eligibleSalary * PF_RATE);
    }

    /**
     * Calculates PF with a customized contribution rate.
     *
     * @param basicSalary basic salary of the employee
     * @param customRate rate between 0.0 and 1.0 (e.g. 0.12 for 12%)
     * @return rounded PF deduction amount
     * @throws exceptions.InvalidDeductionException if basicSalary or customRate is invalid
     */
    public static double calculatePF(double basicSalary, double customRate) {
        DeductionUtils.validateNonNegative(basicSalary, "Basic salary");
        DeductionUtils.validateRate(customRate, "PF rate");

        return DeductionUtils.round(basicSalary * customRate);
    }

    /**
     * Calculates net salary by subtracting total deductions from gross salary.
     *
     * Validates that total deductions do not exceed gross salary.
     *
     * @param grossSalary gross salary of the employee
     * @param totalDeductions total deductions (PF + Tax + others)
     * @return rounded net payable salary
     * @throws ExcessiveDeductionException if totalDeductions > grossSalary
     * @throws exceptions.InvalidDeductionException if grossSalary or totalDeductions is negative, NaN, or Infinite
     */
    public static double calculateNetSalary(double grossSalary, double totalDeductions) {
        DeductionUtils.validateNonNegative(grossSalary, "Gross salary");
        DeductionUtils.validateNonNegative(totalDeductions, "Total deductions");

        if (totalDeductions > grossSalary) {
            throw new ExcessiveDeductionException(grossSalary, totalDeductions);
        }

        return DeductionUtils.round(grossSalary - totalDeductions);
    }

    /**
     * Calculates net salary from gross salary, PF, and tax directly.
     *
     * @param grossSalary gross salary of the employee
     * @param pf provident fund deduction
     * @param tax income tax deduction
     * @return rounded net payable salary
     */
    public static double calculateNetSalary(double grossSalary, double pf, double tax) {
        DeductionUtils.validateNonNegative(pf, "PF deduction");
        DeductionUtils.validateNonNegative(tax, "Tax deduction");
        double totalDeductions = DeductionUtils.round(pf + tax);
        return calculateNetSalary(grossSalary, totalDeductions);
    }

    /**
     * Calculates net salary clamping negative values to zero instead of throwing ExcessiveDeductionException.
     *
     * @param grossSalary gross salary of the employee
     * @param totalDeductions total deductions
     * @return non-negative net payable salary
     */
    public static double calculateNetSalaryClamped(double grossSalary, double totalDeductions) {
        DeductionUtils.validateNonNegative(grossSalary, "Gross salary");
        DeductionUtils.validateNonNegative(totalDeductions, "Total deductions");

        double net = grossSalary - totalDeductions;
        return DeductionUtils.round(Math.max(0.0, net));
    }
}
