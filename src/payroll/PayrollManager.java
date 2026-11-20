package payroll;

import employee.Employee;
import employee.EmployeeManager;
import exceptions.EmployeeNotFoundException;

/**
 * High-level manager coordinating payroll operations between EmployeeManager,
 * PayslipGenerator, and PayrollHistory.
 */
public class PayrollManager {
    private EmployeeManager employeeManager;
    private PayrollHistory payrollHistory;

    public PayrollManager(EmployeeManager employeeManager, PayrollHistory payrollHistory) {
        this.employeeManager = employeeManager;
        this.payrollHistory = payrollHistory;
    }

    /**
     * Generates a payslip for the specified employee ID, prints it, and records it in history.
     */
    public Payslip generatePayslip(int employeeId) throws EmployeeNotFoundException {
        Employee employee = employeeManager.getEmployeeById(employeeId);
        Payslip payslip = PayslipGenerator.generate(employee);
        payrollHistory.addPayslip(payslip);
        payslip.printPayslip();
        return payslip;
    }

    /**
     * Displays complete payroll history.
     */
    public void viewPayrollHistory() {
        payrollHistory.displayHistory();
    }

    /**
     * Generates payslips in batch for all registered employees and records them in history.
     */
    public int generateAllPayslips() {
        java.util.List<Employee> all = employeeManager.getAllEmployees();
        if (all.isEmpty()) {
            System.out.println("\n[!] No registered employees found for batch payroll generation.");
            return 0;
        }

        System.out.println("\n=== BATCH PAYROLL GENERATION IN PROGRESS ===");
        int count = 0;
        for (Employee emp : all) {
            Payslip slip = PayslipGenerator.generate(emp);
            payrollHistory.addPayslip(slip);
            slip.printPayslip();
            count++;
        }
        System.out.println("\n[+] Batch payroll complete: " + count + " payslip(s) generated and recorded.");
        return count;
    }

    /**
     * Displays summary statistics of all generated payroll transactions.
     */
    public void viewPayrollSummary() {
        payrollHistory.displayPayrollSummary();
    }

    public PayrollHistory getPayrollHistory() {
        return payrollHistory;
    }
}
