package deduction;

/**
 * Calculates income tax based on gross salary slabs:
 * - Slab 1 (Gross <= 50,000)             : 0%
 * - Slab 2 (Gross > 50,000 and <= 100,000): 10%
 * - Slab 3 (Gross > 100,000)             : 20%
 *
 * Provides:
 * 1. Standard Slab Tax calculation (Flat rate by bracket, preserving project specification)
 * 2. Progressive Marginal Slab Tax calculation (Real-world taxation where each bracket is taxed incrementally)
 * 3. Validation for negative, NaN, and non-finite gross salary inputs
 * 4. Named constants and TaxSlab enum integration
 * 5. 2-decimal financial rounding precision
 */
public class TaxCalculator {

    public static final double SLAB_1_LIMIT = 50000.0;
    public static final double SLAB_2_LIMIT = 100000.0;

    public static final double SLAB_1_RATE = 0.0;   // 0%
    public static final double SLAB_2_RATE = 0.10;  // 10%
    public static final double SLAB_3_RATE = 0.20;  // 20%

    /**
     * Calculates tax on gross salary using standard bracket rate.
     * Preserves exact compatibility with project rules and README specifications:
     * - Gross <= 50,000 : 0%
     * - 50,000 < Gross <= 100,000 : 10% of gross salary
     * - Gross > 100,000 : 20% of gross salary
     *
     * @param grossSalary gross salary of the employee
     * @return rounded tax deduction amount
     * @throws exceptions.InvalidDeductionException if grossSalary is negative, NaN, or Infinite
     */
    public static double calculateTax(double grossSalary) {
        DeductionUtils.validateNonNegative(grossSalary, "Gross salary");

        TaxSlab slab = TaxSlab.getSlabForGrossSalary(grossSalary);
        double tax = grossSalary * slab.getRate();
        return DeductionUtils.round(tax);
    }

    /**
     * Calculates progressive (marginal) tax where rates apply only to income
     * within each specific bracket:
     * - First 50,000: 0%
     * - 50,001 to 100,000: 10% on amount exceeding 50,000
     * - Above 100,000: 20% on amount exceeding 100,000 + (50,000 * 10%)
     *
     * @param grossSalary gross salary of the employee
     * @return rounded progressive tax deduction amount
     * @throws exceptions.InvalidDeductionException if grossSalary is negative, NaN, or Infinite
     */
    public static double calculateProgressiveTax(double grossSalary) {
        DeductionUtils.validateNonNegative(grossSalary, "Gross salary");

        double tax = 0.0;
        if (grossSalary > SLAB_2_LIMIT) {
            tax += (grossSalary - SLAB_2_LIMIT) * SLAB_3_RATE;
            tax += (SLAB_2_LIMIT - SLAB_1_LIMIT) * SLAB_2_RATE;
        } else if (grossSalary > SLAB_1_LIMIT) {
            tax += (grossSalary - SLAB_1_LIMIT) * SLAB_2_RATE;
        }

        return DeductionUtils.round(tax);
    }

    /**
     * Calculates tax with an explicit choice between progressive marginal tax
     * and flat bracket tax.
     *
     * @param grossSalary gross salary of the employee
     * @param progressive true for progressive marginal tax, false for flat bracket tax
     * @return rounded tax deduction amount
     */
    public static double calculateTax(double grossSalary, boolean progressive) {
        return progressive ? calculateProgressiveTax(grossSalary) : calculateTax(grossSalary);
    }

    /**
     * Retrieves the applicable TaxSlab enum for the given gross salary.
     *
     * @param grossSalary gross salary of the employee
     * @return applicable TaxSlab
     */
    public static TaxSlab getApplicableSlab(double grossSalary) {
        return TaxSlab.getSlabForGrossSalary(grossSalary);
    }

    /**
     * Retrieves the tax rate (e.g. 0.0, 0.10, 0.20) for the given gross salary.
     *
     * @param grossSalary gross salary of the employee
     * @return tax rate as a decimal
     */
    public static double getTaxRate(double grossSalary) {
        return getApplicableSlab(grossSalary).getRate();
    }

    /**
     * Generates a step-by-step textual explanation of the tax calculation for viva / auditing.
     *
     * @param grossSalary gross salary to explain
     * @param progressive whether progressive or flat calculation is used
     * @return explanation string
     */
    public static String explainTaxCalculation(double grossSalary, boolean progressive) {
        DeductionUtils.validateNonNegative(grossSalary, "Gross salary");

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Gross Salary: Rs. %.2f\n", grossSalary));

        if (progressive) {
            sb.append("Calculation Mode: Progressive (Marginal) Tax\n");
            if (grossSalary <= SLAB_1_LIMIT) {
                sb.append(String.format("  - Bracket 1 (0 to %.2f): Exempt (Rs. 0.00)\n", SLAB_1_LIMIT));
                sb.append("Total Tax: Rs. 0.00");
            } else if (grossSalary <= SLAB_2_LIMIT) {
                double taxable = grossSalary - SLAB_1_LIMIT;
                double tax = taxable * SLAB_2_RATE;
                sb.append(String.format("  - Bracket 1 (0 to %.2f): Exempt (Rs. 0.00)\n", SLAB_1_LIMIT));
                sb.append(String.format("  - Bracket 2 (%.2f to %.2f @ 10%% on Rs. %.2f): Rs. %.2f\n",
                        SLAB_1_LIMIT, grossSalary, taxable, tax));
                sb.append(String.format("Total Tax: Rs. %.2f", DeductionUtils.round(tax)));
            } else {
                double slab2Tax = (SLAB_2_LIMIT - SLAB_1_LIMIT) * SLAB_2_RATE;
                double taxableSlab3 = grossSalary - SLAB_2_LIMIT;
                double slab3Tax = taxableSlab3 * SLAB_3_RATE;
                double total = slab2Tax + slab3Tax;
                sb.append(String.format("  - Bracket 1 (0 to %.2f): Exempt (Rs. 0.00)\n", SLAB_1_LIMIT));
                sb.append(String.format("  - Bracket 2 (%.2f to %.2f @ 10%%): Rs. %.2f\n",
                        SLAB_1_LIMIT, SLAB_2_LIMIT, slab2Tax));
                sb.append(String.format("  - Bracket 3 (Above %.2f @ 20%% on Rs. %.2f): Rs. %.2f\n",
                        SLAB_2_LIMIT, taxableSlab3, slab3Tax));
                sb.append(String.format("Total Tax: Rs. %.2f", DeductionUtils.round(total)));
            }
        } else {
            TaxSlab slab = getApplicableSlab(grossSalary);
            double tax = grossSalary * slab.getRate();
            sb.append("Calculation Mode: Flat Bracket Tax\n");
            sb.append(String.format("  - Applicable Slab: %s\n", slab.getDescription()));
            sb.append(String.format("  - Applied Rate: %.0f%%\n", slab.getRate() * 100));
            sb.append(String.format("Total Tax: Rs. %.2f * %.2f = Rs. %.2f",
                    grossSalary, slab.getRate(), DeductionUtils.round(tax)));
        }

        return sb.toString();
    }
}
