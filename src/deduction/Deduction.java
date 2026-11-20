package deduction;

/**
 * Represents deductions applied to an employee's salary.
 * Encapsulates Provident Fund (PF) and Income Tax
 * with defensive validation ensuring non-negative values.
 */
public class Deduction {
    private double pf;
    private double tax;

    public Deduction(double pf, double tax) {
        if (pf < 0 || tax < 0) {
            throw new IllegalArgumentException(
                    String.format("Deduction components cannot be negative. PF: %.2f, Tax: %.2f", pf, tax));
        }
        this.pf = pf;
        this.tax = tax;
    }

    public double getPf() {
        return pf;
    }

    public void setPf(double pf) {
        if (pf < 0) {
            throw new IllegalArgumentException("PF cannot be negative: " + pf);
        }
        this.pf = pf;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        if (tax < 0) {
            throw new IllegalArgumentException("Tax cannot be negative: " + tax);
        }
        this.tax = tax;
    }

    public double getTotalDeduction() {
        return pf + tax;
    }

    @Override
    public String toString() {
        return String.format("Deduction [PF: %.2f, Tax: %.2f, Total: %.2f]", pf, tax, getTotalDeduction());
    }
}
