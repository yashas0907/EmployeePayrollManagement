package salary;

/**
 * Computes standard allowances and gross salary based on basic salary rules:
 * - HRA = 20% of basic salary
 * - DA = 10% of basic salary for Full-Time employees, 0% for Part-Time employees
 * - Gross Salary = Basic Salary + HRA + DA
 */
public class SalaryCalculator {
    public static final double HRA_RATE = 0.20;
    public static final double DA_RATE_FULL_TIME = 0.10;

    public static double calculateHRA(double basicSalary) {
        return basicSalary * HRA_RATE;
    }

    public static double calculateDA(double basicSalary, boolean isFullTime) {
        return isFullTime ? (basicSalary * DA_RATE_FULL_TIME) : 0.0;
    }

    public static double calculateGrossSalary(double basicSalary, double hra, double da) {
        return basicSalary + hra + da;
    }
}
