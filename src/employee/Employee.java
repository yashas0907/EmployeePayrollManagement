package employee;

import salary.Allowance;
import salary.Salary;
import deduction.Deduction;

/**
 * Abstract Base Class representing an Employee.
 * Demonstrates:
 * - Abstraction: Abstract class with abstract methods for specific employee behaviors.
 * - Encapsulation: Private member variables with public getters and setters.
 */
public abstract class Employee {
    private int id;
    private String name;
    private String department;
    private double basicSalary;

    public Employee(int id, String name, String department, double basicSalary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.basicSalary = basicSalary;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    // Abstract methods demonstrating Polymorphism
    public abstract String getEmployeeType();
    public abstract Allowance calculateAllowance();
    public abstract Deduction calculateDeduction();
    public abstract double calculateGrossSalary();
    public abstract double calculateNetSalary();
    public abstract Salary getSalaryBreakdown();

    @Override
    public String toString() {
        return String.format("ID: %-5d | Name: %-16s | Dept: %-12s | Type: %-10s | Basic: %10.2f",
                id, name, department, getEmployeeType(), basicSalary);
    }
}
