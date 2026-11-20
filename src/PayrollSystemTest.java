import employee.Employee;
import employee.EmployeeManager;
import employee.FullTimeEmployee;
import employee.PartTimeEmployee;
import exceptions.DuplicateEmployeeException;
import exceptions.EmployeeNotFoundException;
import exceptions.InvalidSalaryException;
import payroll.PayrollHistory;
import payroll.PayrollManager;
import payroll.Payslip;

import java.util.List;

/**
 * Standalone Automated Test Runner for Employee Payroll Management System.
 * Pure Core Java test suite with zero external dependencies.
 *
 * Verifies:
 * 1. Custom Singly Linked List DSA (add, search ID, search Dept, update, bubble sort, delete)
 * 2. Salary and Allowance calculations (Full-Time vs Part-Time)
 * 3. Tax slabs (0%, 10%, 20%) and Net salary
 * 4. Custom exceptions (DuplicateEmployeeException, InvalidSalaryException, EmployeeNotFoundException)
 * 5. Java Collections Framework & Batch processing (PayrollHistory, aggregations, batch payslips)
 */
public class PayrollSystemTest {
    private static int passedCount = 0;
    private static int failedCount = 0;

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("        RUNNING COMPREHENSIVE PAYROLL SYSTEM TEST SUITE        ");
        System.out.println("===============================================================\n");

        testFullTimeEmployeeCalculations();
        testPartTimeEmployeeCalculations();
        testTaxSlabs();
        testLinkedListAddAndSearch();
        testLinkedListUpdate();
        testLinkedListDepartmentSearch();
        testLinkedListBubbleSort();
        testLinkedListRemoval();
        testDuplicateEmployeeException();
        testInvalidSalaryExceptions();
        testEmployeeNotFoundException();
        testPayrollHistoryAndAggregations();
        testBatchPayslipGeneration();

        System.out.println("\n===============================================================");
        System.out.printf("TEST SUMMARY: Total: %d | PASSED: %d | FAILED: %d\n",
                (passedCount + failedCount), passedCount, failedCount);
        System.out.println("===============================================================");

        if (failedCount > 0) {
            System.err.println("[!] Some tests failed. Please review the output above.");
            System.exit(1);
        } else {
            System.out.println("[+] ALL TESTS PASSED SUCCESSFULLY!");
        }
    }

    private static void testFullTimeEmployeeCalculations() {
        try {
            FullTimeEmployee ft = new FullTimeEmployee(1, "Alice", "IT", 60000.0);
            assertEqual("FT HRA (20%)", ft.calculateAllowance().getHra(), 12000.0);
            assertEqual("FT DA (10%)", ft.calculateAllowance().getDa(), 6000.0);
            assertEqual("FT Gross", ft.calculateGrossSalary(), 78000.0);
            assertEqual("FT PF (12%)", ft.calculateDeduction().getPf(), 7200.0);
            assertEqual("FT Tax (10%)", ft.calculateDeduction().getTax(), 7800.0);
            assertEqual("FT Total Deductions", ft.calculateDeduction().getTotalDeduction(), 15000.0);
            assertEqual("FT Net Salary", ft.calculateNetSalary(), 63000.0);
            pass("Test 1: Full-Time employee calculations (HRA 20%, DA 10%, PF 12%, Tax 10%, Net)");
        } catch (Throwable t) {
            fail("Test 1: Full-Time employee calculations", t);
        }
    }

    private static void testPartTimeEmployeeCalculations() {
        try {
            PartTimeEmployee pt = new PartTimeEmployee(2, "Bob", "HR", 40000.0);
            assertEqual("PT HRA (20%)", pt.calculateAllowance().getHra(), 8000.0);
            assertEqual("PT DA (0%)", pt.calculateAllowance().getDa(), 0.0);
            assertEqual("PT Gross", pt.calculateGrossSalary(), 48000.0);
            assertEqual("PT PF (12%)", pt.calculateDeduction().getPf(), 4800.0);
            assertEqual("PT Tax (0%)", pt.calculateDeduction().getTax(), 0.0);
            assertEqual("PT Total Deductions", pt.calculateDeduction().getTotalDeduction(), 4800.0);
            assertEqual("PT Net Salary", pt.calculateNetSalary(), 43200.0);
            pass("Test 2: Part-Time employee calculations (HRA 20%, DA 0%, PF 12%, Tax 0%, Net)");
        } catch (Throwable t) {
            fail("Test 2: Part-Time employee calculations", t);
        }
    }

    private static void testTaxSlabs() {
        try {
            // Slab 1: Gross <= 50,000 -> 0%
            PartTimeEmployee s1 = new PartTimeEmployee(10, "S1", "Ops", 40000.0); // Gross 48,000
            assertEqual("Tax Slab 1 (<= 50k)", s1.calculateDeduction().getTax(), 0.0);

            // Slab 2: 50,000 < Gross <= 100,000 -> 10%
            FullTimeEmployee s2 = new FullTimeEmployee(11, "S2", "Ops", 60000.0); // Gross 78,000
            assertEqual("Tax Slab 2 (50k-100k)", s2.calculateDeduction().getTax(), 7800.0);

            // Slab 3: Gross > 100,000 -> 20%
            FullTimeEmployee s3 = new FullTimeEmployee(12, "S3", "Ops", 90000.0); // Basic 90k + HRA 18k + DA 9k = Gross 117k
            assertEqual("Tax Slab 3 (> 100k)", s3.calculateDeduction().getTax(), 23400.0);

            pass("Test 3: All 3 Tax Slabs correctly verified (0%, 10%, 20%)");
        } catch (Throwable t) {
            fail("Test 3: Tax slabs", t);
        }
    }

    private static void testLinkedListAddAndSearch() {
        try {
            EmployeeManager em = new EmployeeManager();
            FullTimeEmployee e1 = new FullTimeEmployee(101, "John", "Engineering", 50000.0);
            PartTimeEmployee e2 = new PartTimeEmployee(102, "Sarah", "Marketing", 35000.0);
            em.addEmployee(e1);
            em.addEmployee(e2);

            assertEqual("Manager size", em.getSize(), 2);
            assertEqual("Find by ID 101", em.searchEmployee(101), e1);
            assertEqual("Find by ID 102", em.searchEmployee(102), e2);
            assertNull("Find missing ID", em.searchEmployee(999));
            pass("Test 4: Linked List add and linear search by ID");
        } catch (Throwable t) {
            fail("Test 4: Linked List add and search", t);
        }
    }

    private static void testLinkedListUpdate() {
        try {
            EmployeeManager em = new EmployeeManager();
            FullTimeEmployee e = new FullTimeEmployee(105, "Old Name", "Old Dept", 50000.0);
            em.addEmployee(e);

            em.updateEmployee(105, "New Name", "New Dept", 65000.0);
            Employee updated = em.getEmployeeById(105);

            if (!"New Name".equals(updated.getName())) throw new AssertionError("Name not updated");
            if (!"New Dept".equals(updated.getDepartment())) throw new AssertionError("Dept not updated");
            assertEqual("Updated salary", updated.getBasicSalary(), 65000.0);
            if (!"Full-Time".equals(updated.getEmployeeType())) throw new AssertionError("Type lost during update");

            pass("Test 5: In-place Linked List employee record update (type preserved)");
        } catch (Throwable t) {
            fail("Test 5: Linked list update", t);
        }
    }

    private static void testLinkedListDepartmentSearch() {
        try {
            EmployeeManager em = new EmployeeManager();
            em.addEmployee(new FullTimeEmployee(1, "E1", "IT", 50000.0));
            em.addEmployee(new PartTimeEmployee(2, "E2", "HR", 30000.0));
            em.addEmployee(new FullTimeEmployee(3, "E3", "IT", 60000.0));

            List<Employee> itList = em.searchByDepartment("IT");
            assertEqual("IT count", itList.size(), 2);

            List<Employee> hrList = em.searchByDepartment("hr"); // Case-insensitive
            assertEqual("HR count", hrList.size(), 1);

            List<Employee> none = em.searchByDepartment("Sales");
            assertEqual("Empty dept count", none.size(), 0);

            pass("Test 6: Department search multi-node linear traversal");
        } catch (Throwable t) {
            fail("Test 6: Department search traversal", t);
        }
    }

    private static void testLinkedListBubbleSort() {
        try {
            EmployeeManager em = new EmployeeManager();
            em.addEmployee(new FullTimeEmployee(30, "Low", "IT", 20000.0));
            em.addEmployee(new FullTimeEmployee(10, "High", "IT", 80000.0));
            em.addEmployee(new FullTimeEmployee(20, "Mid", "IT", 50000.0));

            // Sort by Salary descending
            em.sortBySalaryDescending();
            List<Employee> bySal = em.getAllEmployees();
            assertEqual("First by sal", bySal.get(0).getBasicSalary(), 80000.0);
            assertEqual("Second by sal", bySal.get(1).getBasicSalary(), 50000.0);
            assertEqual("Third by sal", bySal.get(2).getBasicSalary(), 20000.0);

            // Sort by ID ascending
            em.sortByIdAscending();
            List<Employee> byId = em.getAllEmployees();
            assertEqual("First by ID", byId.get(0).getId(), 10);
            assertEqual("Second by ID", byId.get(1).getId(), 20);
            assertEqual("Third by ID", byId.get(2).getId(), 30);

            pass("Test 7: In-place Bubble Sort on custom Linked List (by ID & Salary)");
        } catch (Throwable t) {
            fail("Test 7: Linked list bubble sort", t);
        }
    }

    private static void testLinkedListRemoval() {
        try {
            EmployeeManager em = new EmployeeManager();
            em.addEmployee(new FullTimeEmployee(1, "Head", "IT", 50000.0));
            em.addEmployee(new FullTimeEmployee(2, "Middle", "IT", 50000.0));
            em.addEmployee(new FullTimeEmployee(3, "Tail", "IT", 50000.0));

            // Remove middle
            em.removeEmployee(2);
            assertEqual("Size after middle removal", em.getSize(), 2);
            assertNull("Middle removed", em.searchEmployee(2));

            // Remove head
            em.removeEmployee(1);
            assertEqual("Size after head removal", em.getSize(), 1);
            assertNull("Head removed", em.searchEmployee(1));

            // Remove remaining tail
            em.removeEmployee(3);
            assertEqual("Size after tail removal", em.getSize(), 0);
            if (!em.isEmpty()) throw new AssertionError("List should be empty");

            pass("Test 8: Node deletion (head, middle, tail) with pointer re-linking");
        } catch (Throwable t) {
            fail("Test 8: Node removal", t);
        }
    }

    private static void testDuplicateEmployeeException() {
        try {
            EmployeeManager em = new EmployeeManager();
            em.addEmployee(new FullTimeEmployee(100, "Original", "IT", 50000.0));

            boolean caught = false;
            try {
                em.addEmployee(new PartTimeEmployee(100, "Duplicate", "HR", 40000.0));
            } catch (DuplicateEmployeeException e) {
                caught = true;
            }
            if (!caught) throw new AssertionError("DuplicateEmployeeException was not thrown!");
            pass("Test 9: DuplicateEmployeeException on duplicate employee ID");
        } catch (Throwable t) {
            fail("Test 9: DuplicateEmployeeException", t);
        }
    }

    private static void testInvalidSalaryExceptions() {
        try {
            EmployeeManager em = new EmployeeManager();

            assertThrows("Salary 0", () -> em.addEmployee(new FullTimeEmployee(1, "Zero", "IT", 0.0)),
                    InvalidSalaryException.class);
            assertThrows("Salary -500", () -> em.addEmployee(new FullTimeEmployee(2, "Neg", "IT", -500.0)),
                    InvalidSalaryException.class);
            assertThrows("Salary NaN", () -> em.addEmployee(new FullTimeEmployee(3, "NaN", "IT", Double.NaN)),
                    InvalidSalaryException.class);
            assertThrows("Salary Infinity", () -> em.addEmployee(new FullTimeEmployee(4, "Inf", "IT", Double.POSITIVE_INFINITY)),
                    InvalidSalaryException.class);

            pass("Test 10: InvalidSalaryException on <= 0, NaN, and Infinity");
        } catch (Throwable t) {
            fail("Test 10: InvalidSalaryException", t);
        }
    }

    private static void testEmployeeNotFoundException() {
        try {
            EmployeeManager em = new EmployeeManager();
            assertThrows("Search missing", () -> em.getEmployeeById(999), EmployeeNotFoundException.class);
            assertThrows("Remove missing", () -> em.removeEmployee(999), EmployeeNotFoundException.class);
            assertThrows("Update missing", () -> em.updateEmployee(999, "A", "B", 50000.0), EmployeeNotFoundException.class);
            pass("Test 11: EmployeeNotFoundException on search, remove, and update");
        } catch (Throwable t) {
            fail("Test 11: EmployeeNotFoundException", t);
        }
    }

    private static void testPayrollHistoryAndAggregations() {
        try {
            EmployeeManager em = new EmployeeManager();
            PayrollHistory history = new PayrollHistory();
            PayrollManager pm = new PayrollManager(em, history);

            FullTimeEmployee ft = new FullTimeEmployee(1, "Alice", "IT", 60000.0); // Net 63,000, Tax 7,800, PF 7,200
            PartTimeEmployee pt = new PartTimeEmployee(2, "Bob", "HR", 40000.0);   // Net 43,200, Tax 0, PF 4,800
            em.addEmployee(ft);
            em.addEmployee(pt);

            pm.generatePayslip(1);
            pm.generatePayslip(2);

            assertEqual("History record count", history.getRecordCount(), 2);
            assertEqual("Total Net Disbursed", history.getTotalDisbursedSalary(), 63000.0 + 43200.0);
            assertEqual("Total Tax Collected", history.getTotalTaxCollected(), 7800.0);
            assertEqual("Total PF Deposited", history.getTotalPfDeposited(), 7200.0 + 4800.0);

            pass("Test 12: PayrollHistory Collections aggregations (Net, Tax, PF)");
        } catch (Throwable t) {
            fail("Test 12: PayrollHistory aggregations", t);
        }
    }

    private static void testBatchPayslipGeneration() {
        try {
            EmployeeManager em = new EmployeeManager();
            PayrollHistory history = new PayrollHistory();
            PayrollManager pm = new PayrollManager(em, history);

            em.addEmployee(new FullTimeEmployee(1, "E1", "Dept1", 50000.0));
            em.addEmployee(new PartTimeEmployee(2, "E2", "Dept2", 30000.0));
            em.addEmployee(new FullTimeEmployee(3, "E3", "Dept3", 70000.0));

            int count = pm.generateAllPayslips();
            assertEqual("Batch generated count", count, 3);
            assertEqual("History records after batch", history.getRecordCount(), 3);

            pass("Test 13: Batch payslip generation across all registered employees");
        } catch (Throwable t) {
            fail("Test 13: Batch payslip generation", t);
        }
    }

    // Assertion helpers
    private static void assertEqual(String desc, double actual, double expected) {
        if (Math.abs(actual - expected) > 0.01) {
            throw new AssertionError(desc + " failed! Expected: " + expected + ", Got: " + actual);
        }
    }

    private static void assertEqual(String desc, Object actual, Object expected) {
        if (actual == null && expected == null) return;
        if (actual == null || !actual.equals(expected)) {
            throw new AssertionError(desc + " failed! Expected: " + expected + ", Got: " + actual);
        }
    }

    private static void assertNull(String desc, Object obj) {
        if (obj != null) {
            throw new AssertionError(desc + " failed! Expected null, got: " + obj);
        }
    }

    private interface Executable {
        void execute() throws Exception;
    }

    private static void assertThrows(String desc, Executable executable, Class<? extends Exception> expectedType) {
        try {
            executable.execute();
            throw new AssertionError(desc + " failed! Expected exception: " + expectedType.getSimpleName());
        } catch (Exception e) {
            if (!expectedType.isInstance(e)) {
                throw new AssertionError(desc + " failed! Expected " + expectedType.getSimpleName() + " but got " + e.getClass().getSimpleName());
            }
        }
    }

    private static void pass(String msg) {
        System.out.println("[PASS] " + msg);
        passedCount++;
    }

    private static void fail(String msg, Throwable t) {
        System.err.println("[FAIL] " + msg + ": " + t.getMessage());
        t.printStackTrace();
        failedCount++;
    }
}
