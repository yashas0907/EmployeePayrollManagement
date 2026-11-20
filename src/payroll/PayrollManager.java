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

    public PayrollHistory getPayrollHistory() {
        return payrollHistory;
    }
}
