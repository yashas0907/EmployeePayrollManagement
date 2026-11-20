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

        System.out.println("\n===============================================================================================");
        System.out.println("                             PAYROLL TRANSACTION HISTORY");
        System.out.println("===============================================================================================");
        System.out.printf("%-9s | %-5s | %-16s | %-10s | %10s | %10s | %10s | %-11s\n",
                "Slip ID", "EmpID", "Employee Name", "Type", "Gross", "Deductions", "Net Pay", "Date");
        System.out.println("-----------------------------------------------------------------------------------------------");

        for (Payslip p : historyList) {
            System.out.printf("%-9s | %-5d | %-16s | %-10s | %10.2f | %10.2f | %10.2f | %-11s\n",
                    p.getPayslipId(), p.getEmployeeId(), p.getEmployeeName(),
                    p.getEmployeeType(), p.getGrossSalary(), p.getTotalDeductions(),
                    p.getNetSalary(), p.getGenerationDate());
        }
        System.out.println("===============================================================================================");
        System.out.println("Total Records: " + historyList.size());
    }

    public boolean isEmpty() {
        return historyList.isEmpty();
    }

    public int getRecordCount() {
        return historyList.size();
    }

    public double getTotalDisbursedSalary() {
        double total = 0.0;
        for (Payslip p : historyList) {
            total += p.getNetSalary();
        }
        return total;
    }

    public double getTotalTaxCollected() {
        double total = 0.0;
        for (Payslip p : historyList) {
            total += p.getTax();
        }
        return total;
    }

    public double getTotalPfDeposited() {
        double total = 0.0;
        for (Payslip p : historyList) {
            total += p.getPf();
        }
        return total;
    }

    public void displayPayrollSummary() {
        if (historyList.isEmpty()) {
            System.out.println("\n[!] No payroll transactions recorded yet.");
            return;
        }

        double totalGross = 0.0;
        for (Payslip p : historyList) {
            totalGross += p.getGrossSalary();
        }

        System.out.println("\n+-------------------------------------------------------------+");
        System.out.println("|                 CUMULATIVE PAYROLL SUMMARY                  |");
        System.out.println("+-------------------------------------------------------------+");
        System.out.printf("| Total Transactions: %-39d |\n", historyList.size());
        System.out.printf("| Total Gross Disbursed:                Rs. %17.2f |\n", totalGross);
        System.out.printf("| Total PF Deposited:                   Rs. %17.2f |\n", getTotalPfDeposited());
        System.out.printf("| Total Tax Collected:                  Rs. %17.2f |\n", getTotalTaxCollected());
        System.out.println("+-------------------------------------------------------------+");
        System.out.printf("| NET TOTAL DISBURSED:                  Rs. %17.2f |\n", getTotalDisbursedSalary());
        System.out.println("+-------------------------------------------------------------+");
    }
}
