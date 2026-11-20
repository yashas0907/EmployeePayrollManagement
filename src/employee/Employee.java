package employee;

import salary.Allowance;
import salary.Salary;
import salary.SalaryCalculator;
import deduction.Deduction;
import deduction.TaxCalculator;
import deduction.NetSalaryCalculator;

/**
 * Abstract Base Class representing an Employee.
 * Demonstrates:
 * - Abstraction: Abstract class with abstract methods for specific employee behaviors.
 * - Encapsulation: Private member variables with public getters and setters.
 * - Inheritance: Provides concrete, shared salary calculation logic to all subclasses.
 */
public abstract class Employee {
    private final int id;
    private String name;
    private String department;
    private double basicSalary;

    public Employee(int id, String name, String department, double basicSalary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.basicSalary = basicSalary;
    }

    // Getters and Setters (ID has only a getter to ensure immutability)
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    // Abstract methods defining type-specific behavior (Polymorphism)
    public abstract String getEmployeeType();
    public abstract Allowance calculateAllowance();

    // Concrete shared salary calculations inherited by all employee types
    public double calculateGrossSalary() {
        Allowance allowance = calculateAllowance();
        return SalaryCalculator.calculateGrossSalary(getBasicSalary(), allowance.getHra(), allowance.getDa());
    }

    public Deduction calculateDeduction() {
        double gross = calculateGrossSalary();
        double pf = NetSalaryCalculator.calculatePF(getBasicSalary());
        double tax = TaxCalculator.calculateTax(gross);
        return new Deduction(pf, tax);
    }

    public double calculateNetSalary() {
        double gross = calculateGrossSalary();
        Deduction deduction = calculateDeduction();
        return NetSalaryCalculator.calculateNetSalary(gross, deduction.getTotalDeduction());
    }

    public Salary getSalaryBreakdown() {
        Allowance allowance = calculateAllowance();
        double gross = calculateGrossSalary();
        Deduction deduction = calculateDeduction();
        double net = calculateNetSalary();
        return new Salary(getBasicSalary(), allowance, gross, deduction, net);
    }

    @Override
    public String toString() {
        return String.format("ID: %-5d | Name: %-16s | Dept: %-12s | Type: %-10s | Basic: %10.2f",
                id, name, department, getEmployeeType(), basicSalary);
    }
}
