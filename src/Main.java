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
import java.util.Scanner;

/**
 * Main Class — Console-based User Interface for Employee Payroll Management System.
 *
 * Menu Options:
 * 1. Add Employee (Full-Time / Part-Time)
 * 2. View / Sort Employees (Current, by ID, by Salary)
 * 3. Search Employee (by ID or Department)
 * 4. Update Employee Details
 * 5. Remove Employee (with confirmation)
 * 6. Generate Payslip (Single or Batch)
 * 7. View Payroll History & Summary
 * 8. Exit
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
            int choice = readIntegerInput("Enter your choice (1-8): ");

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
                    handleUpdateEmployee();
                    break;
                case 5:
                    handleRemoveEmployee();
                    break;
                case 6:
                    handleGeneratePayslip();
                    break;
                case 7:
                    handleViewPayrollHistory();
                    break;
                case 8:
                    System.out.println("\nThank you for using Employee Payroll Management System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("\n[!] Invalid choice! Please select a number between 1 and 8.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n+-------------------------------------------------------------+");
        System.out.println("|                         MAIN MENU                           |");
        System.out.println("+-------------------------------------------------------------+");
        System.out.println("| 1. Add Employee                                             |");
        System.out.println("| 2. View / Sort Employees                                    |");
        System.out.println("| 3. Search Employee (by ID or Dept)                          |");
        System.out.println("| 4. Update Employee Details                                  |");
        System.out.println("| 5. Remove Employee                                          |");
        System.out.println("| 6. Generate Payslip (Single / Batch)                        |");
        System.out.println("| 7. View Payroll History & Summary                           |");
        System.out.println("| 8. Exit                                                     |");
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

        Integer id = readPositiveEmployeeId("Enter Employee ID: ");
        if (id == null) {
            return;
        }

        String name = readNonEmptyString("Enter Employee Name: ");
        if (name.isEmpty()) {
            return;
        }

        String department = readNonEmptyString("Enter Department: ");
        if (department.isEmpty()) {
            return;
        }

        Double basicSalary = readSalaryInput("Enter Basic Salary (Rs.): ");
        if (basicSalary == null) {
            return;
        }

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
        } catch (IllegalArgumentException e) {
            System.out.println("\n[Error - Invalid Input] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n[Error] Failed to add employee: " + e.getMessage());
        }
    }

    private static void handleViewEmployees() {
        System.out.println("\nView Employees Options:");
        System.out.println("1. Display Current List");
        System.out.println("2. Sort by ID (Ascending)");
        System.out.println("3. Sort by Salary (Descending)");
        int sortChoice = readIntegerInput("Enter choice (1-3): ");

        if (sortChoice == 2) {
            employeeManager.sortByIdAscending();
            System.out.println("[*] List sorted by Employee ID in ascending order.");
        } else if (sortChoice == 3) {
            employeeManager.sortBySalaryDescending();
            System.out.println("[*] List sorted by Basic Salary in descending order.");
        }
        employeeManager.displayAllEmployees();
    }

    private static void handleSearchEmployee() {
        System.out.println("\n--- Search Employee ---");
        System.out.println("1. Search by Employee ID");
        System.out.println("2. Search by Department");
        int searchChoice = readIntegerInput("Enter choice (1 or 2): ");

        if (searchChoice == 2) {
            String dept = readNonEmptyString("Enter Department to search: ");
            List<Employee> results = employeeManager.searchByDepartment(dept);
            if (results.isEmpty()) {
                System.out.println("\n[!] No employees found in department: " + dept);
            } else {
                System.out.println("\n[+] Found " + results.size() + " employee(s) in " + dept + ":");
                for (Employee e : results) {
                    System.out.println(e);
                }
            }
            return;
        }

        Integer id = readPositiveEmployeeId("Enter Employee ID to search: ");
        if (id == null) {
            return;
        }
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

    private static void handleUpdateEmployee() {
        System.out.println("\n--- Update Employee Details ---");
        Integer id = readPositiveEmployeeId("Enter Employee ID to update: ");
        if (id == null) {
            return;
        }

        try {
            Employee currentEmp = employeeManager.getEmployeeById(id);
            System.out.printf("Current Record: %s (%s) | Dept: %s | Salary: Rs. %.2f\n",
                    currentEmp.getName(), currentEmp.getEmployeeType(),
                    currentEmp.getDepartment(), currentEmp.getBasicSalary());

            String newName = readNonEmptyString("Enter New Name: ");
            String newDept = readNonEmptyString("Enter New Department: ");
            Double newSalary = readSalaryInput("Enter New Basic Salary (Rs.): ");
            if (newSalary == null) {
                return;
            }

            employeeManager.updateEmployee(id, newName, newDept, newSalary);
            System.out.println("\n[+] Success: Employee details updated successfully!");
            System.out.println(currentEmp);
        } catch (EmployeeNotFoundException e) {
            System.out.println("\n[Error - Not Found] " + e.getMessage());
        } catch (InvalidSalaryException e) {
            System.out.println("\n[Error - Invalid Salary] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n[Error] Update failed: " + e.getMessage());
        }
    }

    private static void handleRemoveEmployee() {
        System.out.println("\n--- Remove Employee ---");
        Integer id = readPositiveEmployeeId("Enter Employee ID to remove: ");
        if (id == null) {
            return;
        }

        try {
            Employee emp = employeeManager.getEmployeeById(id);
            System.out.printf("Found: ID %d - %s (%s, Dept: %s)\n",
                    emp.getId(), emp.getName(), emp.getEmployeeType(), emp.getDepartment());
            System.out.print("Are you sure you want to remove this employee? (y/n): ");
            if (!scanner.hasNextLine()) {
                return;
            }
            String confirm = scanner.nextLine().trim();
            if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
                employeeManager.removeEmployee(id);
                System.out.println("\n[+] Success: Employee with ID " + id + " has been removed successfully.");
            } else {
                System.out.println("[*] Removal cancelled by user.");
            }
        } catch (EmployeeNotFoundException e) {
            System.out.println("\n[Error - Not Found] " + e.getMessage());
        }
    }

    private static void handleGeneratePayslip() {
        System.out.println("\n--- Generate Payslip ---");
        System.out.print("Enter Employee ID (or enter 0 to generate for ALL employees): ");
        if (!scanner.hasNextLine()) {
            return;
        }
        String input = scanner.nextLine().trim();
        try {
            int id = Integer.parseInt(input);
            if (id == 0) {
                payrollManager.generateAllPayslips();
                return;
            }
            if (id < 0) {
                System.out.println("[!] Invalid ID: " + id + ". ID must be a strictly positive integer (> 0).");
                return;
            }
            payrollManager.generatePayslip(id);
            System.out.println("[+] Payslip generated and recorded in Payroll History.");
        } catch (NumberFormatException e) {
            System.out.println("[!] Invalid input. Please enter a valid numeric ID.");
        } catch (EmployeeNotFoundException e) {
            System.out.println("\n[Error - Not Found] " + e.getMessage());
        }
    }

    private static void handleViewPayrollHistory() {
        System.out.println("\n--- Payroll History Menu ---");
        System.out.println("1. View Complete Transaction History");
        System.out.println("2. Filter History by Employee ID");
        System.out.println("3. View Cumulative Financial Summary");
        int opt = readIntegerInput("Enter option (1-3): ");

        if (opt == 2) {
            Integer id = readPositiveEmployeeId("Enter Employee ID to filter history: ");
            if (id == null) {
                return;
            }
            List<Payslip> list = payrollHistory.getHistoryByEmployeeId(id);
            if (list.isEmpty()) {
                System.out.println("\n[!] No history records found for Employee ID: " + id);
            } else {
                System.out.println("\n[+] Found " + list.size() + " record(s) for Employee ID " + id + ":");
                for (Payslip p : list) {
                    System.out.println(p);
                }
            }
        } else if (opt == 3) {
            payrollManager.viewPayrollSummary();
        } else {
            payrollManager.viewPayrollHistory();
        }
    }

    // Input validation helper methods
    private static int readIntegerInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                return 8; // Cleanly exit if input stream ends at menu
            }
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid input. Please enter a valid whole number.");
            }
        }
    }

    /**
     * Reads a strictly positive Employee ID (> 0).
     * Rejects zero and negative numbers.
     * Returns null if stream is terminated, avoiding -1 conflict.
     */
    private static Integer readPositiveEmployeeId(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                return null;
            }
            String input = scanner.nextLine().trim();
            try {
                int id = Integer.parseInt(input);
                if (id <= 0) {
                    System.out.println("[!] Invalid ID: " + id + ". Employee ID must be a strictly positive integer (> 0).");
                    continue;
                }
                return id;
            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid input. Please enter a valid whole number for Employee ID.");
            }
        }
    }

    /**
     * Reads basic salary ensuring it is finite (not NaN, not Infinity) and strictly positive (> 0).
     */
    private static Double readSalaryInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                return null;
            }
            String input = scanner.nextLine().trim();
            try {
                double salary = Double.parseDouble(input);
                if (Double.isNaN(salary) || Double.isInfinite(salary)) {
                    System.out.println("[!] Invalid salary: " + input + ". NaN and Infinity are not allowed.");
                    continue;
                }
                if (salary <= 0.0) {
                    System.out.println("[!] Invalid salary: " + salary + ". Basic salary must be strictly greater than 0.");
                    continue;
                }
                return salary;
            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid input. Please enter a valid numeric value for salary.");
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
