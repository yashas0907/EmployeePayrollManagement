package deduction;

/**
 * Represents deductions applied to an employee's salary.
 * Encapsulates Provident Fund (PF) and Income Tax.
 */
public class Deduction {
    private double pf;
    private double tax;

    public Deduction(double pf, double tax) {
        this.pf = pf;
        this.tax = tax;
    }

    public double getPf() {
        return pf;
    }

    public void setPf(double pf) {
        this.pf = pf;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
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
