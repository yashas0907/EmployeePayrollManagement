package salary;

import deduction.Deduction;

/**
 * Represents the complete salary breakdown for an employee.
 * Encapsulates basic salary, allowances, gross salary, deductions, and net salary.
 */
public class Salary {
    private double basicSalary;
    private Allowance allowance;
    private double grossSalary;
    private Deduction deduction;
    private double netSalary;

    public Salary(double basicSalary, Allowance allowance, double grossSalary, Deduction deduction, double netSalary) {
        this.basicSalary = basicSalary;
        this.allowance = allowance;
        this.grossSalary = grossSalary;
        this.deduction = deduction;
        this.netSalary = netSalary;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public Allowance getAllowance() {
        return allowance;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public Deduction getDeduction() {
        return deduction;
    }

    public double getNetSalary() {
        return netSalary;
    }

    @Override
    public String toString() {
        return String.format("Basic: %.2f | Gross: %.2f | Net: %.2f", basicSalary, grossSalary, netSalary);
    }
}
