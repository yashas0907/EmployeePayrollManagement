package salary;

/**
 * Represents the allowances granted to an employee.
 * Encapsulates HRA (House Rent Allowance) and DA (Dearness Allowance).
 */
public class Allowance {
    private double hra;
    private double da;

    public Allowance(double hra, double da) {
        this.hra = hra;
        this.da = da;
    }

    public double getHra() {
        return hra;
    }

    public void setHra(double hra) {
        this.hra = hra;
    }

    public double getDa() {
        return da;
    }

    public void setDa(double da) {
        this.da = da;
    }

    public double getTotalAllowance() {
        return hra + da;
    }

    @Override
    public String toString() {
        return String.format("Allowance [HRA: %.2f, DA: %.2f, Total: %.2f]", hra, da, getTotalAllowance());
    }
}
