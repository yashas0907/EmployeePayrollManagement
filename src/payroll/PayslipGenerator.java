package payroll;

import employee.Employee;
import salary.Salary;

/**
 * Generates Payslip instances for employees.
 * Assigns unique payslip reference IDs and computes all components.
 */
public class PayslipGenerator {
    private static int payslipCounter = 1001;

    public static Payslip generate(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }

        Salary salary = employee.getSalaryBreakdown();
        String payslipId = "PS-" + (payslipCounter++);

        return new Payslip(
                payslipId,
                employee.getId(),
                employee.getName(),
                employee.getDepartment(),
                employee.getEmployeeType(),
                salary.getBasicSalary(),
                salary.getAllowance().getHra(),
                salary.getAllowance().getDa(),
                salary.getGrossSalary(),
                salary.getDeduction().getPf(),
                salary.getDeduction().getTax(),
                salary.getDeduction().getTotalDeduction(),
                salary.getNetSalary()
        );
    }
}
