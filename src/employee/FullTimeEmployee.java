package employee;

import salary.Allowance;
import salary.SalaryCalculator;

/**
 * Represents a Full-Time Employee.
 * Demonstrates:
 * - Inheritance: Extends Employee base class and inherits common salary calculations.
 * - Polymorphism: Overrides type-specific methods:
 *   - Receives HRA (20% of basic)
 *   - Receives DA (10% of basic)
 */
public class FullTimeEmployee extends Employee {

    public FullTimeEmployee(int id, String name, String department, double basicSalary) {
        super(id, name, department, basicSalary);
    }

    @Override
    public String getEmployeeType() {
        return "Full-Time";
    }

    @Override
    public Allowance calculateAllowance() {
        double hra = SalaryCalculator.calculateHRA(getBasicSalary());
        double da = SalaryCalculator.calculateDA(getBasicSalary(), true);
        return new Allowance(hra, da);
    }
}
