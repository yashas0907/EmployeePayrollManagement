package test;

import deduction.Deduction;
import deduction.DeductionUtils;
import deduction.NetSalaryCalculator;
import deduction.TaxCalculator;
import deduction.TaxSlab;
import employee.FullTimeEmployee;
import employee.PartTimeEmployee;
import exceptions.DuplicateEmployeeException;
import exceptions.EmployeeNotFoundException;
import exceptions.ExcessiveDeductionException;
import exceptions.InvalidDeductionException;
import exceptions.InvalidSalaryException;

/**
 * Standalone automated test suite verifying:
 * - Provident Fund (PF) calculations and edge cases
 * - Income Tax slabs and tax calculations (both flat and progressive)
 * - Net salary calculations, excessive deduction detection, and clamping
 * - Encapsulation and validation in Deduction model
 * - Custom exceptions hierarchy and payload integrity
 * - Full system integration with FullTimeEmployee and PartTimeEmployee
 */
public class DeductionModuleTest {

    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("     RUNNING DEDUCTION, TAX & EXCEPTION MODULE TEST SUITE     ");
        System.out.println("===============================================================\n");

        testPFCalculationStandard();
        testPFCalculationStatutoryCeiling();
        testPFCalculationEdgeCases();
        testPFRoundingPrecision();

        testTaxSlabsEnum();
        testFlatTaxCalculation();
        testProgressiveTaxCalculation();
        testTaxBoundaryAndEdgeCases();
        testTaxExplanation();

        testNetSalaryCalculation();
        testExcessiveDeductionHandling();
        testNetSalaryClamped();
        testNetSalaryNegativeInputs();

        testDeductionModelEncapsulation();
        testDeductionValidation();

        testCustomExceptionsHierarchy();

        testEmployeeModuleIntegration();

        System.out.println("\n===============================================================");
        System.out.printf("TEST SUMMARY: Total: %d | Passed: %d | Failed: %d\n",
                totalTests, passedTests, failedTests);
        System.out.println("===============================================================");

        if (failedTests > 0) {
            System.err.println("[FAIL] Some tests failed!");
            System.exit(1);
        } else {
            System.out.println("[SUCCESS] All deduction, tax, and exception module tests passed!");
        }
    }

    // -------------------------------------------------------------
    // Assertion Helper Methods
    // -------------------------------------------------------------
    private static void assertEquals(String testName, double expected, double actual) {
        totalTests++;
        double epsilon = 0.0001;
        if (Math.abs(expected - actual) <= epsilon) {
            passedTests++;
            System.out.printf("  [PASS] %s (Expected: %.2f, Got: %.2f)\n", testName, expected, actual);
        } else {
            failedTests++;
            System.err.printf("  [FAIL] %s - Expected: %.2f, but got: %.2f\n", testName, expected, actual);
        }
    }

    private static void assertEquals(String testName, Object expected, Object actual) {
        totalTests++;
        if (expected == null && actual == null || (expected != null && expected.equals(actual))) {
            passedTests++;
            System.out.printf("  [PASS] %s (Got: %s)\n", testName, actual);
        } else {
            failedTests++;
            System.err.printf("  [FAIL] %s - Expected: %s, but got: %s\n", testName, expected, actual);
        }
    }

    private static void assertTrue(String testName, boolean condition) {
        totalTests++;
        if (condition) {
            passedTests++;
            System.out.printf("  [PASS] %s\n", testName);
        } else {
            failedTests++;
            System.err.printf("  [FAIL] %s - Expected true condition\n", testName);
        }
    }

    // -------------------------------------------------------------
    // 1. PF Calculation Tests
    // -------------------------------------------------------------
    private static void testPFCalculationStandard() {
        System.out.println(">> Testing Standard PF Calculation (12% of basic)...");
        assertEquals("PF for Rs. 60,000 basic (Full-Time README)", 7200.0, NetSalaryCalculator.calculatePF(60000.0));
        assertEquals("PF for Rs. 40,000 basic (Part-Time README)", 4800.0, NetSalaryCalculator.calculatePF(40000.0));
        assertEquals("PF for Rs. 15,000 basic", 1800.0, NetSalaryCalculator.calculatePF(15000.0));
        assertEquals("PF for Rs. 0 basic", 0.0, NetSalaryCalculator.calculatePF(0.0));
    }

    private static void testPFCalculationStatutoryCeiling() {
        System.out.println("\n>> Testing PF with EPFO Statutory Wage Ceiling (Rs. 15,000)...");
        // Below ceiling: uses full basic
        assertEquals("PF for Rs. 10,000 with ceiling", 1200.0, NetSalaryCalculator.calculatePF(10000.0, true));
        assertEquals("PF for Rs. 15,000 with ceiling", 1800.0, NetSalaryCalculator.calculatePF(15000.0, true));
        // Above ceiling: capped at 15,000 * 0.12 = 1,800
        assertEquals("PF for Rs. 60,000 capped at ceiling", 1800.0, NetSalaryCalculator.calculatePF(60000.0, true));
        assertEquals("PF for Rs. 100,000 capped at ceiling", 1800.0, NetSalaryCalculator.calculatePF(100000.0, true));
    }

    private static void testPFCalculationEdgeCases() {
        System.out.println("\n>> Testing PF Edge Cases & Validation...");
        boolean caughtNegative = false;
        try {
            NetSalaryCalculator.calculatePF(-5000.0);
        } catch (InvalidDeductionException e) {
            caughtNegative = true;
        }
        assertTrue("Negative basic salary throws InvalidDeductionException", caughtNegative);

        boolean caughtNaN = false;
        try {
            NetSalaryCalculator.calculatePF(Double.NaN);
        } catch (InvalidDeductionException e) {
            caughtNaN = true;
        }
        assertTrue("NaN basic salary throws InvalidDeductionException", caughtNaN);
    }

    private static void testPFRoundingPrecision() {
        System.out.println("\n>> Testing PF Financial Rounding Precision...");
        // 55555.55 * 0.12 = 6666.666 -> rounds to 6666.67
        assertEquals("PF fractional rounding HALF_UP", 6666.67, NetSalaryCalculator.calculatePF(55555.55));
    }

    // -------------------------------------------------------------
    // 2. Tax Slabs & Tax Calculation Tests
    // -------------------------------------------------------------
    private static void testTaxSlabsEnum() {
        System.out.println("\n>> Testing Tax Slabs Mapping...");
        assertEquals("Slab for Rs. 30,000", TaxSlab.EXEMPT, TaxSlab.getSlabForGrossSalary(30000.0));
        assertEquals("Slab for boundary Rs. 50,000", TaxSlab.EXEMPT, TaxSlab.getSlabForGrossSalary(50000.0));
        assertEquals("Slab for Rs. 50,000.01", TaxSlab.MODERATE, TaxSlab.getSlabForGrossSalary(50000.01));
        assertEquals("Slab for Rs. 78,000", TaxSlab.MODERATE, TaxSlab.getSlabForGrossSalary(78000.0));
        assertEquals("Slab for boundary Rs. 100,000", TaxSlab.MODERATE, TaxSlab.getSlabForGrossSalary(100000.0));
        assertEquals("Slab for Rs. 100,000.01", TaxSlab.HIGH, TaxSlab.getSlabForGrossSalary(100000.01));
        assertEquals("Slab for Rs. 150,000", TaxSlab.HIGH, TaxSlab.getSlabForGrossSalary(150000.0));
    }

    private static void testFlatTaxCalculation() {
        System.out.println("\n>> Testing Flat Bracket Tax Calculation (Project Spec)...");
        assertEquals("Tax for Rs. 48,000 gross (0%)", 0.0, TaxCalculator.calculateTax(48000.0));
        assertEquals("Tax for Rs. 50,000 gross (0%)", 0.0, TaxCalculator.calculateTax(50000.0));
        assertEquals("Tax for Rs. 78,000 gross (10% = 7,800)", 7800.0, TaxCalculator.calculateTax(78000.0));
        assertEquals("Tax for Rs. 100,000 gross (10% = 10,000)", 10000.0, TaxCalculator.calculateTax(100000.0));
        assertEquals("Tax for Rs. 120,000 gross (20% = 24,000)", 24000.0, TaxCalculator.calculateTax(120000.0));
    }

    private static void testProgressiveTaxCalculation() {
        System.out.println("\n>> Testing Progressive (Marginal) Tax Calculation...");
        // 0 to 50k: 0
        assertEquals("Progressive tax for Rs. 48,000", 0.0, TaxCalculator.calculateProgressiveTax(48000.0));
        assertEquals("Progressive tax for Rs. 50,000", 0.0, TaxCalculator.calculateProgressiveTax(50000.0));

        // 78,000: 0 on first 50k + 10% on (78k - 50k = 28k) = 2,800
        assertEquals("Progressive tax for Rs. 78,000", 2800.0, TaxCalculator.calculateProgressiveTax(78000.0));

        // 100,000: 0 on first 50k + 10% on (100k - 50k = 50k) = 5,000
        assertEquals("Progressive tax for Rs. 100,000", 5000.0, TaxCalculator.calculateProgressiveTax(100000.0));

        // 120,000: 5,000 (from slab 2) + 20% on (120k - 100k = 20k) = 5,000 + 4,000 = 9,000
        assertEquals("Progressive tax for Rs. 120,000", 9000.0, TaxCalculator.calculateProgressiveTax(120000.0));
    }

    private static void testTaxBoundaryAndEdgeCases() {
        System.out.println("\n>> Testing Tax Boundaries & Error Handling...");
        assertEquals("Tax for 0 gross", 0.0, TaxCalculator.calculateTax(0.0));

        boolean caughtNegative = false;
        try {
            TaxCalculator.calculateTax(-10000.0);
        } catch (InvalidDeductionException e) {
            caughtNegative = true;
        }
        assertTrue("Negative gross throws InvalidDeductionException", caughtNegative);

        boolean caughtProgressiveNegative = false;
        try {
            TaxCalculator.calculateProgressiveTax(-500.0);
        } catch (InvalidDeductionException e) {
            caughtProgressiveNegative = true;
        }
        assertTrue("Negative gross in progressive tax throws InvalidDeductionException", caughtProgressiveNegative);
    }

    private static void testTaxExplanation() {
        System.out.println("\n>> Testing Tax Explanation Generator for Viva...");
        String flatExplanation = TaxCalculator.explainTaxCalculation(78000.0, false);
        assertTrue("Flat explanation mentions 10%", flatExplanation.contains("10%"));

        String progExplanation = TaxCalculator.explainTaxCalculation(78000.0, true);
        assertTrue("Progressive explanation mentions Bracket 1", progExplanation.contains("Bracket 1"));
        assertTrue("Progressive explanation mentions Bracket 2", progExplanation.contains("Bracket 2"));
    }

    // -------------------------------------------------------------
    // 3. Net Salary Calculation Tests
    // -------------------------------------------------------------
    private static void testNetSalaryCalculation() {
        System.out.println("\n>> Testing Net Salary Calculation...");
        // Full-time sample: Gross 78,000 - Total Deductions 15,000 = 63,000
        assertEquals("Net Salary: 78k - 15k deductions", 63000.0,
                NetSalaryCalculator.calculateNetSalary(78000.0, 15000.0));

        // Part-time sample: Gross 48,000 - Total Deductions 4,800 = 43,200
        assertEquals("Net Salary: 48k - 4.8k deductions", 43200.0,
                NetSalaryCalculator.calculateNetSalary(48000.0, 4800.0));

        // Direct overload with Gross, PF, Tax
        assertEquals("Net Salary via (gross, pf, tax)", 63000.0,
                NetSalaryCalculator.calculateNetSalary(78000.0, 7200.0, 7800.0));
    }

    private static void testExcessiveDeductionHandling() {
        System.out.println("\n>> Testing Excessive Deductions Detection...");
        boolean caughtExcessive = false;
        try {
            // Gross 50,000 but Deductions 65,000 (shortfall 15,000)
            NetSalaryCalculator.calculateNetSalary(50000.0, 65000.0);
        } catch (ExcessiveDeductionException e) {
            caughtExcessive = true;
            assertEquals("Excessive shortfall recorded correctly", 15000.0, e.getShortfall());
            assertEquals("Gross salary in exception", 50000.0, e.getGrossSalary());
            assertEquals("Total deductions in exception", 65000.0, e.getTotalDeductions());
        }
        assertTrue("Deductions > Gross throws ExcessiveDeductionException", caughtExcessive);
    }

    private static void testNetSalaryClamped() {
        System.out.println("\n>> Testing Clamped Net Salary (Non-Throwing)...");
        assertEquals("Clamped Net Salary for 50k - 65k deductions returns 0.0",
                0.0, NetSalaryCalculator.calculateNetSalaryClamped(50000.0, 65000.0));
        assertEquals("Clamped Net Salary for normal values works",
                63000.0, NetSalaryCalculator.calculateNetSalaryClamped(78000.0, 15000.0));
    }

    private static void testNetSalaryNegativeInputs() {
        System.out.println("\n>> Testing Net Salary Negative Inputs...");
        boolean caughtNegGross = false;
        try {
            NetSalaryCalculator.calculateNetSalary(-1000.0, 200.0);
        } catch (InvalidDeductionException e) {
            caughtNegGross = true;
        }
        assertTrue("Negative gross throws InvalidDeductionException", caughtNegGross);

        boolean caughtNegDeductions = false;
        try {
            NetSalaryCalculator.calculateNetSalary(50000.0, -200.0);
        } catch (InvalidDeductionException e) {
            caughtNegDeductions = true;
        }
        assertTrue("Negative deductions throw InvalidDeductionException", caughtNegDeductions);
    }

    // -------------------------------------------------------------
    // 4. Deduction Model Tests
    // -------------------------------------------------------------
    private static void testDeductionModelEncapsulation() {
        System.out.println("\n>> Testing Deduction Model Encapsulation & Total...");
        Deduction d = new Deduction(7200.0, 7800.0);
        assertEquals("Deduction PF", 7200.0, d.getPf());
        assertEquals("Deduction Tax", 7800.0, d.getTax());
        assertEquals("Deduction Total", 15000.0, d.getTotalDeduction());

        d.setPf(8000.555);
        assertEquals("PF rounded to 2 decimals via setter", 8000.56, d.getPf());

        d.setTax(2000.444);
        assertEquals("Tax rounded to 2 decimals via setter", 2000.44, d.getTax());
        assertEquals("Total rounded accurately", 10001.0, d.getTotalDeduction());

        // Copy and equality
        Deduction copy = d.copy();
        assertEquals("Deduction equals method", d, copy);
        assertEquals("Deduction hashCode equality", d.hashCode(), copy.hashCode());
    }

    private static void testDeductionValidation() {
        System.out.println("\n>> Testing Deduction Validation in Model...");
        boolean caughtNegPF = false;
        try {
            new Deduction(-100.0, 500.0);
        } catch (InvalidDeductionException e) {
            caughtNegPF = true;
        }
        assertTrue("Negative PF in constructor throws InvalidDeductionException", caughtNegPF);

        boolean caughtNegTax = false;
        try {
            new Deduction(500.0, -100.0);
        } catch (InvalidDeductionException e) {
            caughtNegTax = true;
        }
        assertTrue("Negative Tax in constructor throws InvalidDeductionException", caughtNegTax);
    }

    // -------------------------------------------------------------
    // 5. Custom Exceptions Hierarchy Tests
    // -------------------------------------------------------------
    private static void testCustomExceptionsHierarchy() {
        System.out.println("\n>> Testing Custom Exceptions Hierarchy...");
        ExcessiveDeductionException ex = new ExcessiveDeductionException(50000.0, 60000.0);
        assertTrue("ExcessiveDeductionException is an InvalidDeductionException",
                ex instanceof InvalidDeductionException);
        assertTrue("InvalidDeductionException is an IllegalArgumentException",
                ex instanceof IllegalArgumentException);
        assertTrue("IllegalArgumentException is a RuntimeException",
                ex instanceof RuntimeException);

        InvalidSalaryException salEx = new InvalidSalaryException("Negative salary", -1000.0);
        assertEquals("InvalidSalaryException records invalid salary", -1000.0, salEx.getInvalidSalary());
        assertTrue("InvalidSalaryException is a checked Exception", salEx instanceof Exception);

        DuplicateEmployeeException dupEx = new DuplicateEmployeeException("Duplicate ID 101", 101);
        assertEquals("DuplicateEmployeeException records ID", 101, dupEx.getDuplicateId());

        EmployeeNotFoundException notFoundEx = new EmployeeNotFoundException("ID not found", 999);
        assertEquals("EmployeeNotFoundException records ID", 999, notFoundEx.getSearchId());
    }

    // -------------------------------------------------------------
    // 6. Integration with Employee Domain Model
    // -------------------------------------------------------------
    private static void testEmployeeModuleIntegration() {
        System.out.println("\n>> Testing Integration with FullTime & PartTime Employees...");

        // FullTimeEmployee integration
        FullTimeEmployee ft = new FullTimeEmployee(1, "Alice", "Engineering", 60000.0);
        assertEquals("FullTime HRA (20%)", 12000.0, ft.calculateAllowance().getHra());
        assertEquals("FullTime DA (10%)", 6000.0, ft.calculateAllowance().getDa());
        assertEquals("FullTime Gross Salary", 78000.0, ft.calculateGrossSalary());
        assertEquals("FullTime PF (12%)", 7200.0, ft.calculateDeduction().getPf());
        assertEquals("FullTime Tax (10% of 78k)", 7800.0, ft.calculateDeduction().getTax());
        assertEquals("FullTime Total Deductions", 15000.0, ft.calculateDeduction().getTotalDeduction());
        assertEquals("FullTime Net Salary", 63000.0, ft.calculateNetSalary());

        // PartTimeEmployee integration
        PartTimeEmployee pt = new PartTimeEmployee(2, "Bob", "Support", 40000.0);
        assertEquals("PartTime HRA (20%)", 8000.0, pt.calculateAllowance().getHra());
        assertEquals("PartTime DA (0%)", 0.0, pt.calculateAllowance().getDa());
        assertEquals("PartTime Gross Salary", 48000.0, pt.calculateGrossSalary());
        assertEquals("PartTime PF (12%)", 4800.0, pt.calculateDeduction().getPf());
        assertEquals("PartTime Tax (0% since gross <= 50k)", 0.0, pt.calculateDeduction().getTax());
        assertEquals("PartTime Total Deductions", 4800.0, pt.calculateDeduction().getTotalDeduction());
        assertEquals("PartTime Net Salary", 43200.0, pt.calculateNetSalary());
    }
}
