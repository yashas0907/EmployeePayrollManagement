package payroll;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an itemized Payslip for an employee.
 * Encapsulates all earnings, allowances, deductions, and net salary.
 */
public class Payslip {
    private String payslipId;
    private int employeeId;
    private String employeeName;
    private String department;
    private String employeeType;
    private double basicSalary;
    private double hra;
    private double da;
    private double grossSalary;
    private double pf;
    private double tax;
    private double totalDeductions;
    private double netSalary;
    private String generationDate;

    public Payslip(String payslipId, int employeeId, String employeeName, String department,
                   String employeeType, double basicSalary, double hra, double da,
                   double grossSalary, double pf, double tax, double totalDeductions,
                   double netSalary) {
        this.payslipId = payslipId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.employeeType = employeeType;
        this.basicSalary = basicSalary;
        this.hra = hra;
        this.da = da;
        this.grossSalary = grossSalary;
        this.pf = pf;
        this.tax = tax;
        this.totalDeductions = totalDeductions;
        this.netSalary = netSalary;
        this.generationDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
    }

    public String getPayslipId() { return payslipId; }
    public int getEmployeeId() { return employeeId; }
    public String getEmployeeName() { return employeeName; }
    public String getDepartment() { return department; }
    public String getEmployeeType() { return employeeType; }
    public double getBasicSalary() { return basicSalary; }
    public double getHra() { return hra; }
    public double getDa() { return da; }
    public double getGrossSalary() { return grossSalary; }
    public double getPf() { return pf; }
    public double getTax() { return tax; }
    public double getTotalDeductions() { return totalDeductions; }
    public double getNetSalary() { return netSalary; }
    public String getGenerationDate() { return generationDate; }

    public void printPayslip() {
        System.out.println("\n+-------------------------------------------------------------+");
        System.out.println("|                    EMPLOYEE PAYSLIP                         |");
        System.out.println("+-------------------------------------------------------------+");
        System.out.printf("| Payslip ID: %-15s Date: %-22s |\n", payslipId, generationDate);
        System.out.printf("| Employee ID: %-14d Name: %-22s |\n", employeeId, employeeName);
        System.out.printf("| Department: %-15s Type: %-22s |\n", department, employeeType);
        System.out.println("+------------------------------+------------------------------+");
        System.out.println("| EARNINGS                     | DEDUCTIONS                   |");
        System.out.println("+------------------------------+------------------------------+");
        System.out.printf("| Basic Salary:  %12.2f  | Provident Fund (PF):%8.2f |\n", basicSalary, pf);
        System.out.printf("| HRA (20%%):    %12.2f  | Income Tax:         %8.2f |\n", hra, tax);
        System.out.printf("| DA (%s):      %12.2f  |                              |\n",
                employeeType.equalsIgnoreCase("Full-Time") ? "10%" : " 0%", da);
        System.out.println("+------------------------------+------------------------------+");
        System.out.printf("| Gross Salary:  %12.2f  | Total Deductions:   %8.2f |\n", grossSalary, totalDeductions);
        System.out.println("+------------------------------+------------------------------+");
        System.out.printf("| NET PAYABLE SALARY:                   Rs. %17.2f |\n", netSalary);
        System.out.println("+-------------------------------------------------------------+");
    }

    @Override
    public String toString() {
        return String.format("[%s] ID: %d | Name: %s | Gross: Rs. %.2f | Deductions: Rs. %.2f | Net: Rs. %.2f | Date: %s",
                payslipId, employeeId, employeeName, grossSalary, totalDeductions, netSalary, generationDate);
    }
}
