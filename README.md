# Employee Payroll Management System

A console-based Core Java application designed to manage employee records, compute dynamic salaries with real-world allowances and tax deductions, generate itemized payslips, and maintain payroll transaction history.

Developed with pure Core Java (no external frameworks or databases) adhering strictly to Object-Oriented Programming (OOP) principles, a custom Data Structure implemented from scratch, Java Collections, and custom exception handling.

---

## 1. Project Overview

The Employee Payroll Management System provides a clean, robust, and beginner-friendly solution for organizations to manage full-time and part-time staff. It features automated salary calculations incorporating:
- House Rent Allowance (HRA)
- Dearness Allowance (DA)
- Provident Fund (PF)
- Slab-based Income Tax deductions
- Detailed printable Payslips
- Historical transaction logging

---

## 2. Key Features

1. **Add Employee**: Register new Full-Time or Part-Time employees with automated validation for unique IDs and positive salary figures.
2. **View Employees**: Display all registered employees in a clean tabular view directly from the custom singly linked list.
3. **Search Employee**: Search for an employee by ID and view complete profile details and current salary breakdown.
4. **Remove Employee**: Unlink and delete an employee record by ID with proper exception handling if not found.
5. **Generate Payslip**: Calculate gross earnings, deductions, and net salary, printing a formatted payslip and saving the transaction to payroll history.
6. **View Payroll History**: View all generated payslips across sessions using Java Collections (`ArrayList`).
7. **Input Validation**: Clean handling of invalid numeric inputs, negative salaries, duplicate records, and non-existent IDs.

---

## 3. Project Structure

```
EmployeePayrollManagement/
├── src/
│   ├── employee/
│   │   ├── Employee.java
│   │   ├── FullTimeEmployee.java
│   │   ├── PartTimeEmployee.java
│   │   └── EmployeeManager.java
│   ├── salary/
│   │   ├── Salary.java
│   │   ├── SalaryCalculator.java
│   │   └── Allowance.java
│   ├── deduction/
│   │   ├── Deduction.java
│   │   ├── TaxCalculator.java
│   │   └── NetSalaryCalculator.java
│   ├── payroll/
│   │   ├── Payslip.java
│   │   ├── PayslipGenerator.java
│   │   ├── PayrollHistory.java
│   │   └── PayrollManager.java
│   ├── exceptions/
│   │   ├── DuplicateEmployeeException.java
│   │   ├── InvalidSalaryException.java
│   │   └── EmployeeNotFoundException.java
│   └── Main.java
└── README.md
```

---

## 4. Object-Oriented Programming (OOP) Concepts Used

- **Abstraction**:
  - `Employee` is an abstract base class defining the contract for employee behavior (`calculateAllowance()`, `calculateDeduction()`, `calculateGrossSalary()`, `calculateNetSalary()`, `getEmployeeType()`).
  - Implementation details are hidden from callers like `EmployeeManager` and `Main`.
- **Encapsulation**:
  - All domain fields (e.g., `id`, `name`, `basicSalary`, `hra`, `da`, `pf`, `tax`) are declared `private`.
  - Controlled read and write access is exposed via public getter and setter methods.
- **Inheritance**:
  - `FullTimeEmployee` and `PartTimeEmployee` inherit core attributes and common methods from the `Employee` base class using the `extends` keyword.
  - Custom exceptions inherit from `java.lang.Exception`.
- **Polymorphism**:
  - Dynamic Method Dispatch (Runtime Polymorphism): `FullTimeEmployee` and `PartTimeEmployee` provide specialized implementations for `calculateAllowance()` and `getEmployeeType()`.
  - Calling `employee.calculateNetSalary()` executes the appropriate subclass logic dynamically.

---

## 5. Custom Data Structure (DSA from Scratch)

To fulfill academic and faculty requirements, employee records are stored in a **Custom Singly Linked List** implemented inside `EmployeeManager.java`:

- **Node Structure**:
  ```java
  private static class Node {
      Employee data;
      Node next;
  }
  ```
- **Operations Implemented**:
  - **Insertion**: Appends new nodes to the tail of the linked list ($O(n)$ traversal, preserving insertion order).
  - **Search**: Linear traversal through nodes comparing employee IDs ($O(n)$ time complexity).
  - **Deletion**: Linear traversal updating pointers to bypass and unlink target nodes ($O(n)$ time complexity).
  - **Display / Traversal**: Iterating from `head` through `next` pointers to display formatted output.

---

## 6. Java Collections Framework

- **`ArrayList<Payslip>`** in `PayrollHistory.java`:
  - Used meaningfully to maintain chronological records of all generated payslips.
  - Provides dynamic array resizing, random access, and filtering by employee ID.

---

## 7. Custom Exceptions

Three domain-specific checked exceptions are defined and handled with `try-catch` blocks:

1. **`DuplicateEmployeeException`**: Thrown when attempting to register an employee whose ID already exists in the singly linked list.
2. **`InvalidSalaryException`**: Thrown when an entered basic salary is less than or equal to 0.
3. **`EmployeeNotFoundException`**: Thrown when searching, removing, or generating a payslip for a non-existent employee ID.

---

## 8. Salary Calculation Rules

The financial logic is strictly consistent across the entire application:

| Component | Rule / Rate |
| :--- | :--- |
| **HRA (House Rent Allowance)** | 20% of Basic Salary (Both Full-Time and Part-Time) |
| **DA (Dearness Allowance)** | 10% of Basic Salary for **Full-Time**; **0%** for **Part-Time** |
| **Gross Salary** | `Basic Salary + HRA + DA` |
| **PF (Provident Fund)** | 12% of Basic Salary |
| **Income Tax** | Calculated on **Gross Salary**: <br>• Gross ≤ 50,000: **0%** <br>• 50,000 < Gross ≤ 100,000: **10%** <br>• Gross > 100,000: **20%** |
| **Net Salary** | `Gross Salary - PF - Income Tax` |

### Calculation Example:
- **Full-Time Employee** with Basic Salary = ₹60,000:
  - HRA (20%) = ₹12,000
  - DA (10%) = ₹6,000
  - Gross Salary = ₹60,000 + ₹12,000 + ₹6,000 = **₹78,000**
  - PF (12% of basic) = ₹7,200
  - Tax (10% of gross ₹78,000) = ₹7,800
  - Total Deductions = ₹7,200 + ₹7,800 = ₹15,000
  - Net Payable Salary = ₹78,000 - ₹15,000 = **₹63,000**

- **Part-Time Employee** with Basic Salary = ₹40,000:
  - HRA (20%) = ₹8,000
  - DA (0%) = ₹0
  - Gross Salary = ₹40,000 + ₹8,000 + ₹0 = **₹48,000**
  - PF (12% of basic) = ₹4,800
  - Tax (0% since Gross ≤ 50,000) = ₹0
  - Total Deductions = ₹4,800
  - Net Payable Salary = ₹48,000 - ₹4,800 = **₹43,200**

---

## 9. Compilation and Execution Instructions

### Prerequisites
- Java Development Kit (JDK 8 or higher, e.g. JDK 17, 21).

### Step 1: Open Terminal
Navigate to the root directory `EmployeePayrollManagement`:
```bash
cd EmployeePayrollManagement
```

### Step 2: Compile All Sources
Compile all packages into an output directory `bin`:
```bash
# Windows (Command Prompt or PowerShell)
javac -d bin src/exceptions/*.java src/salary/*.java src/deduction/*.java src/employee/*.java src/payroll/*.java src/Main.java

# macOS / Linux
javac -d bin src/exceptions/*.java src/salary/*.java src/deduction/*.java src/employee/*.java src/payroll/*.java src/Main.java
```

### Step 3: Run the Application
```bash
# Windows / macOS / Linux
java -cp bin Main
```

---

## 10. Sample Menu Interface

```text
===============================================================
      WELCOME TO EMPLOYEE PAYROLL MANAGEMENT SYSTEM           
===============================================================

+-------------------------------------------------------------+
|                         MAIN MENU                           |
+-------------------------------------------------------------+
| 1. Add Employee                                             |
| 2. View Employees                                           |
| 3. Search Employee                                          |
| 4. Remove Employee                                          |
| 5. Generate Payslip                                         |
| 6. View Payroll History                                     |
| 7. Exit                                                     |
+-------------------------------------------------------------+
Enter your choice (1-7): 
```
