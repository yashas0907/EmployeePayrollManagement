package employee;

import salary.Allowance;
import salary.Salary;
import salary.SalaryCalculator;
import deduction.Deduction;
import deduction.TaxCalculator;
import deduction.NetSalaryCalculator;

/**
 * Represents a Part-Time Employee.
 * Demonstrates:
 * - Inheritance: Extends Employee base class.
 * - Polymorphism: Overrides abstract methods to implement part-time specific allowance logic:
 *   - Receives HRA (20% of basic)
 *   - Does NOT receive DA (DA = 0)
 */
public class PartTimeEmployee extends Employee {

    public PartTimeEmployee(int id, String name, String department, double basicSalary) {
        super(id, name, department, basicSalary);
    }

    @Override
    public String getEmployeeType() {
        return "Part-Time";
    }

    @Override
    public Allowance calculateAllowance() {
        double hra = SalaryCalculator.calculateHRA(getBasicSalary());
        double da = SalaryCalculator.calculateDA(getBasicSalary(), false); // 0.0 for Part-Time
        return new Allowance(hra, da);
    }

    @Override
    public double calculateGrossSalary() {
        Allowance allowance = calculateAllowance();
        return SalaryCalculator.calculateGrossSalary(getBasicSalary(), allowance.getHra(), allowance.getDa());
    }

    @Override
    public Deduction calculateDeduction() {
        double gross = calculateGrossSalary();
        double pf = NetSalaryCalculator.calculatePF(getBasicSalary());
        double tax = TaxCalculator.calculateTax(gross);
        return new Deduction(pf, tax);
    }

    @Override
    public double calculateNetSalary() {
        double gross = calculateGrossSalary();
        Deduction deduction = calculateDeduction();
        return NetSalaryCalculator.calculateNetSalary(gross, deduction.getTotalDeduction());
    }

    @Override
    public Salary getSalaryBreakdown() {
        Allowance allowance = calculateAllowance();
        double gross = calculateGrossSalary();
        Deduction deduction = calculateDeduction();
        double net = calculateNetSalary();
        return new Salary(getBasicSalary(), allowance, gross, deduction, net);
    }
}
