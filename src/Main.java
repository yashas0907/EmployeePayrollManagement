import employee.Employee;
import employee.EmployeeManager;
import employee.FullTimeEmployee;
import employee.PartTimeEmployee;
import exceptions.DuplicateEmployeeException;
import exceptions.EmployeeNotFoundException;
import exceptions.InvalidSalaryException;
import payroll.PayrollHistory;
import payroll.PayrollManager;

import java.util.Scanner;

/**
 * Main Class — Console-based User Interface for Employee Payroll Management System.
 *
 * Menu Options:
 * 1. Add Employee (Full-Time / Part-Time)
 * 2. View Employees
 * 3. Search Employee
 * 4. Remove Employee
 * 5. Generate Payslip
 * 6. View Payroll History
 * 7. Exit
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EmployeeManager employeeManager = new EmployeeManager();
    private static final PayrollHistory payrollHistory = new PayrollHistory();
    private static final PayrollManager payrollManager = new PayrollManager(employeeManager, payrollHistory);

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("      WELCOME TO EMPLOYEE PAYROLL MANAGEMENT SYSTEM           ");
        System.out.println("===============================================================");

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = readIntegerInput("Enter your choice (1-7): ");

            switch (choice) {
                case 1:
                    handleAddEmployee();
                    break;
                case 2:
                    handleViewEmployees();
                    break;
                case 3:
                    handleSearchEmployee();
                    break;
                case 4:
                    handleRemoveEmployee();
                    break;
                case 5:
                    handleGeneratePayslip();
                    break;
                case 6:
                    handleViewPayrollHistory();
                    break;
                case 7:
                    System.out.println("\nThank you for using Employee Payroll Management System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("\n[!] Invalid choice! Please select a number between 1 and 7.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n+-------------------------------------------------------------+");
        System.out.println("|                         MAIN MENU                           |");
        System.out.println("+-------------------------------------------------------------+");
        System.out.println("| 1. Add Employee                                             |");
        System.out.println("| 2. View Employees                                           |");
        System.out.println("| 3. Search Employee                                          |");
        System.out.println("| 4. Remove Employee                                          |");
        System.out.println("| 5. Generate Payslip                                         |");
        System.out.println("| 6. View Payroll History                                     |");
        System.out.println("| 7. Exit                                                     |");
        System.out.println("+-------------------------------------------------------------+");
    }

    private static void handleAddEmployee() {
        System.out.println("\n--- Add New Employee ---");
        System.out.println("Select Employee Type:");
        System.out.println("1. Full-Time Employee (HRA: 20%, DA: 10%, PF: 12%)");
        System.out.println("2. Part-Time Employee (HRA: 20%, DA:  0%, PF: 12%)");
        int typeChoice = readIntegerInput("Enter type (1 or 2): ");

        if (typeChoice != 1 && typeChoice != 2) {
            System.out.println("[!] Invalid employee type selected. Operation aborted.");
            return;
        }

        int id = readIntegerInput("Enter Employee ID: ");
        String name = readNonEmptyString("Enter Employee Name: ");
        String department = readNonEmptyString("Enter Department: ");
        double basicSalary = readDoubleInput("Enter Basic Salary (Rs.): ");

        try {
            Employee employee;
            if (typeChoice == 1) {
                employee = new FullTimeEmployee(id, name, department, basicSalary);
            } else {
                employee = new PartTimeEmployee(id, name, department, basicSalary);
            }

            employeeManager.addEmployee(employee);
            System.out.println("\n[+] Success: " + employee.getEmployeeType() + " Employee added successfully!");
            System.out.println(employee);
        } catch (DuplicateEmployeeException e) {
            System.out.println("\n[Error - Duplicate ID] " + e.getMessage());
        } catch (InvalidSalaryException e) {
            System.out.println("\n[Error - Invalid Salary] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n[Error] Failed to add employee: " + e.getMessage());
        }
    }

    private static void handleViewEmployees() {
        employeeManager.displayAllEmployees();
    }

    private static void handleSearchEmployee() {
        System.out.println("\n--- Search Employee ---");
        int id = readIntegerInput("Enter Employee ID to search: ");
        try {
            Employee emp = employeeManager.getEmployeeById(id);
            System.out.println("\n[+] Employee Found:");
            System.out.println("-------------------------------------------------------------");
            System.out.println("ID:            " + emp.getId());
            System.out.println("Name:          " + emp.getName());
            System.out.println("Department:    " + emp.getDepartment());
            System.out.println("Type:          " + emp.getEmployeeType());
            System.out.printf("Basic Salary:  Rs. %.2f\n", emp.getBasicSalary());
            System.out.printf("Gross Salary:  Rs. %.2f\n", emp.calculateGrossSalary());
            System.out.printf("Net Salary:    Rs. %.2f\n", emp.calculateNetSalary());
            System.out.println("-------------------------------------------------------------");
        } catch (EmployeeNotFoundException e) {
            System.out.println("\n[Error - Not Found] " + e.getMessage());
        }
    }

    private static void handleRemoveEmployee() {
        System.out.println("\n--- Remove Employee ---");
        int id = readIntegerInput("Enter Employee ID to remove: ");
        try {
            employeeManager.removeEmployee(id);
            System.out.println("\n[+] Success: Employee with ID " + id + " has been removed successfully.");
        } catch (EmployeeNotFoundException e) {
            System.out.println("\n[Error - Not Found] " + e.getMessage());
        }
    }

    private static void handleGeneratePayslip() {
        System.out.println("\n--- Generate Payslip ---");
        int id = readIntegerInput("Enter Employee ID for payslip generation: ");
        try {
            payrollManager.generatePayslip(id);
            System.out.println("[+] Payslip generated and recorded in Payroll History.");
        } catch (EmployeeNotFoundException e) {
            System.out.println("\n[Error - Not Found] " + e.getMessage());
        }
    }

    private static void handleViewPayrollHistory() {
        payrollManager.viewPayrollHistory();
    }

    // Input validation helper methods
    private static int readIntegerInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                return -1;
            }
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid input. Please enter a valid whole number.");
            }
        }
    }

    private static double readDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                return -1;
            }
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid input. Please enter a valid numeric value.");
            }
        }
    }

    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                return "";
            }
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("[!] Input cannot be empty. Please try again.");
        }
    }
}
