package deduction;

/**
 * Calculates tax based on gross salary slabs:
 * - Gross <= 50,000 : 0%
 * - Gross > 50,000 and <= 100,000 : 10%
 * - Gross > 100,000 : 20%
 */
public class TaxCalculator {
    public static double calculateTax(double grossSalary) {
        if (grossSalary <= 50000.0) {
            return 0.0;
        } else if (grossSalary <= 100000.0) {
            return grossSalary * 0.10;
        } else {
            return grossSalary * 0.20;
        }
    }
}
