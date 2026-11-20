package salary;

/**
 * Represents the allowances granted to an employee.
 * Encapsulates HRA (House Rent Allowance) and DA (Dearness Allowance)
 * with defensive validation ensuring non-negative values.
 */
public class Allowance {
    private double hra;
    private double da;

    public Allowance(double hra, double da) {
        if (hra < 0 || da < 0) {
            throw new IllegalArgumentException(
                    String.format("Allowance components cannot be negative. HRA: %.2f, DA: %.2f", hra, da));
        }
        this.hra = hra;
        this.da = da;
    }

    public double getHra() {
        return hra;
    }

    public void setHra(double hra) {
        if (hra < 0) {
            throw new IllegalArgumentException("HRA cannot be negative: " + hra);
        }
        this.hra = hra;
    }

    public double getDa() {
        return da;
    }

    public void setDa(double da) {
        if (da < 0) {
            throw new IllegalArgumentException("DA cannot be negative: " + da);
        }
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
