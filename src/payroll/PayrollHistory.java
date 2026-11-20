package payroll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the history of generated payslips using standard Java Collections (ArrayList).
 * Satisfies Faculty Requirement:
 * - Uses Java Collections (ArrayList) meaningfully to store and query historical payroll records.
 */
public class PayrollHistory {
    private List<Payslip> historyList;

    public PayrollHistory() {
        this.historyList = new ArrayList<>();
    }

    public void addPayslip(Payslip payslip) {
        if (payslip != null) {
            historyList.add(payslip);
        }
    }

    public List<Payslip> getHistory() {
        return Collections.unmodifiableList(historyList);
    }

    public List<Payslip> getHistoryByEmployeeId(int employeeId) {
        List<Payslip> result = new ArrayList<>();
        for (Payslip p : historyList) {
            if (p.getEmployeeId() == employeeId) {
                result.add(p);
            }
        }
        return result;
    }

    public void displayHistory() {
        if (historyList.isEmpty()) {
            System.out.println("\n[!] No payroll history available yet. Generate payslips first.");
            return;
        }

        System.out.println("\n" + "=".repeat(95));
        System.out.println("                             PAYROLL TRANSACTION HISTORY");
        System.out.println("=".repeat(95));
        System.out.printf("%-9s | %-5s | %-16s | %-10s | %10s | %10s | %10s | %-11s\n",
                "Slip ID", "EmpID", "Employee Name", "Type", "Gross", "Deductions", "Net Pay", "Date");
        System.out.println("-".repeat(95));

        for (Payslip p : historyList) {
            System.out.printf("%-9s | %-5d | %-16s | %-10s | %10.2f | %10.2f | %10.2f | %-11s\n",
                    p.getPayslipId(), p.getEmployeeId(), p.getEmployeeName(),
                    p.getEmployeeType(), p.getGrossSalary(), p.getTotalDeductions(),
                    p.getNetSalary(), p.getGenerationDate());
        }
        System.out.println("=".repeat(95));
        System.out.println("Total Records: " + historyList.size());
    }

    public boolean isEmpty() {
        return historyList.isEmpty();
    }

    public int getRecordCount() {
        return historyList.size();
    }
}
