package deduction;

import java.util.Objects;

/**
 * Represents deductions applied to an employee's salary.
 * Encapsulates Provident Fund (PF) and Income Tax.
 *
 * Demonstrates:
 * - Encapsulation: Private member variables with validated getters and setters.
 * - Value Object Semantics: Validated fields, financial rounding, equals, and hashCode.
 */
public class Deduction {
    private double pf;
    private double tax;

    /**
     * Default constructor initializing deductions to zero.
     */
    public Deduction() {
        this.pf = 0.0;
        this.tax = 0.0;
    }

    /**
     * Parameterized constructor with validation and rounding.
     *
     * @param pf Provident Fund deduction amount (must be >= 0)
     * @param tax Income Tax deduction amount (must be >= 0)
     * @throws exceptions.InvalidDeductionException if pf or tax is negative, NaN, or Infinite
     */
    public Deduction(double pf, double tax) {
        setPf(pf);
        setTax(tax);
    }

    public double getPf() {
        return pf;
    }

    public void setPf(double pf) {
        DeductionUtils.validateNonNegative(pf, "Provident Fund (PF)");
        this.pf = DeductionUtils.round(pf);
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        DeductionUtils.validateNonNegative(tax, "Income Tax");
        this.tax = DeductionUtils.round(tax);
    }

    /**
     * Computes total deductions (PF + Tax) rounded to 2 decimal places.
     *
     * @return sum of PF and Tax
     */
    public double getTotalDeduction() {
        return DeductionUtils.round(pf + tax);
    }

    /**
     * Creates a defensive copy of this Deduction instance.
     *
     * @return a new Deduction object with identical values
     */
    public Deduction copy() {
        return new Deduction(this.pf, this.tax);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deduction)) return false;
        Deduction deduction = (Deduction) o;
        return Double.compare(deduction.pf, pf) == 0 &&
               Double.compare(deduction.tax, tax) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pf, tax);
    }

    @Override
    public String toString() {
        return String.format("Deduction [PF: %.2f, Tax: %.2f, Total: %.2f]", pf, tax, getTotalDeduction());
    }
}
