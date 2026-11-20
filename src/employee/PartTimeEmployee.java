package employee;

import salary.Allowance;
import salary.SalaryCalculator;

/**
 * Represents a Part-Time Employee.
 * Demonstrates:
 * - Inheritance: Extends Employee base class and inherits common salary calculations.
 * - Polymorphism: Overrides type-specific methods:
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
}
