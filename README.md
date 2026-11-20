# Employee Payroll Management System

A console-based Core Java application for managing employee records, computing salaries with real-world allowances and tax deductions, generating itemized payslips, and maintaining payroll transaction history.

Built with **pure Core Java** — no external frameworks, no database, no GUI. Demonstrates OOP, a custom Data Structure from scratch, the Java Collections Framework, and custom exception handling.

---

## Features

1. **Add Employee** — Register Full-Time or Part-Time employees with strict ID and salary validation.
2. **View / Sort Employees** — Display employees in insertion order, sorted by ID (ascending), or sorted by Basic Salary (descending).
3. **Search Employee** — Look up employees by ID or filter by department name (case-insensitive).
4. **Update Employee Details** — Modify an employee's name, department, and salary in-place while preserving their type and linked-list position.
5. **Remove Employee** — Delete an employee record with an interactive confirmation prompt to prevent accidental deletion.
6. **Generate Payslip** — Generate a formatted payslip for a single employee or run batch generation for all employees at once.
7. **View Payroll History & Summary** — View the complete payslip transaction log, filter history by employee ID, or display aggregate financial totals (gross, PF, tax, net).
8. **Exit**

---

## Project Structure

```
EmployeePayrollManagement/
├── src/
│   ├── employee/
│   │   ├── Employee.java              # Abstract base class (Abstraction, Encapsulation)
│   │   ├── FullTimeEmployee.java      # Subclass: HRA 20% + DA 10%
│   │   ├── PartTimeEmployee.java      # Subclass: HRA 20% + DA 0%
│   │   └── EmployeeManager.java       # Custom Singly Linked List DSA
│   ├── salary/
│   │   ├── Salary.java                # Salary breakdown model
│   │   ├── SalaryCalculator.java      # HRA, DA, Gross calculation
│   │   └── Allowance.java             # Allowance model with validation
│   ├── deduction/
│   │   ├── Deduction.java             # Deduction model (PF + Tax) with validation
│   │   ├── TaxCalculator.java         # Tax slab logic
│   │   └── NetSalaryCalculator.java   # PF and Net Salary calculation
│   ├── payroll/
│   │   ├── Payslip.java               # Payslip model and formatted print
│   │   ├── PayslipGenerator.java      # Builds Payslip objects, assigns IDs
│   │   ├── PayrollHistory.java        # ArrayList-based transaction log & aggregations
│   │   └── PayrollManager.java        # Coordinator: single & batch payslip ops
│   ├── exceptions/
│   │   ├── DuplicateEmployeeException.java
│   │   ├── InvalidSalaryException.java
│   │   └── EmployeeNotFoundException.java
│   ├── Main.java                      # Console UI and menu
│   └── PayrollSystemTest.java         # Standalone automated test suite (13 tests)
└── README.md
```

---

## OOP Concepts Demonstrated

| Concept | Where |
| :--- | :--- |
| **Abstraction** | `Employee` is abstract. Callers like `Main` and `PayrollManager` work through the `Employee` reference without knowing the concrete type. |
| **Encapsulation** | All fields in `Employee`, `Allowance`, `Deduction`, `Salary`, `Payslip` are `private` with controlled public getters/setters including validation. |
| **Inheritance** | `FullTimeEmployee` and `PartTimeEmployee` extend `Employee`, inheriting all shared salary calculations. Only `calculateAllowance()` and `getEmployeeType()` are overridden. |
| **Polymorphism** | A `List<Employee>` can hold both `FullTimeEmployee` and `PartTimeEmployee` objects. Calling `emp.calculateGrossSalary()` automatically uses the correct allowance logic for each type at runtime. |

---

## Custom Data Structure (DSA from Scratch)

**Singly Linked List** implemented in `EmployeeManager.java`:

```
head -> [Node: E1] -> [Node: E2] -> [Node: E3] -> null
```

| Operation | Method | Time Complexity |
| :--- | :--- | :--- |
| Insert (at tail) | `addEmployee()` | O(n) |
| Search by ID | `searchEmployee(id)` | O(n) |
| Search by Department | `searchByDepartment(dept)` | O(n) |
| Update in-place | `updateEmployee(id, ...)` | O(n) |
| Delete by ID | `removeEmployee(id)` | O(n) |
| Sort by ID or Salary | `sortByIdAscending()` / `sortBySalaryDescending()` | O(n²) Bubble Sort |
| Display all | `displayAllEmployees()` | O(n) |

> **Why a custom Linked List?** To satisfy the DSA faculty requirement: implementing pointer manipulation (`head`, `Node.next`) from scratch instead of relying on Java's built-in `LinkedList`.

---

## Java Collections Framework

`ArrayList<Payslip>` is used in `PayrollHistory.java` to maintain a chronological transaction log of all generated payslips.

| Feature | Usage |
| :--- | :--- |
| `ArrayList.add()` | Record each generated payslip |
| `ArrayList` iteration | Filter by employee ID, compute aggregates |
| `Collections.unmodifiableList()` | Defensive read-only view for external callers |
| Aggregate methods | `getTotalDisbursedSalary()`, `getTotalTaxCollected()`, `getTotalPfDeposited()` |

> **Why ArrayList for history but custom LinkedList for employees?** The LinkedList demonstrates DSA from scratch. The ArrayList is the standard Java Collections Framework choice for sequential records with fast random access and dynamic resizing — both are used deliberately for different purposes.

---

## Custom Exceptions

All three exceptions extend `java.lang.Exception` (checked), include `serialVersionUID`, and provide three constructors each:

| Exception | When Thrown |
| :--- | :--- |
| `DuplicateEmployeeException` | Adding an employee whose ID already exists in the linked list |
| `InvalidSalaryException` | Salary is `<= 0`, `NaN`, or `Infinity` |
| `EmployeeNotFoundException` | Search, remove, or update for a non-existent employee ID |

---

## Salary Calculation Rules

All rules are enforced uniformly through `SalaryCalculator`, `TaxCalculator`, and `NetSalaryCalculator`.

| Component | Rule |
| :--- | :--- |
| **HRA** | 20% of Basic Salary (both types) |
| **DA** | 10% of Basic Salary for Full-Time; **0%** for Part-Time |
| **Gross Salary** | Basic + HRA + DA |
| **PF (Provident Fund)** | 12% of Basic Salary |
| **Income Tax** | On Gross Salary: ≤ 50,000 → **0%** · 50,001–1,00,000 → **10%** · > 1,00,000 → **20%** |
| **Net Salary** | Gross − PF − Tax |

### Worked Example

**Full-Time, Basic = ₹60,000:**

| Step | Calculation | Amount |
| :--- | :--- | ---: |
| HRA | 20% × 60,000 | ₹12,000 |
| DA | 10% × 60,000 | ₹6,000 |
| **Gross Salary** | 60,000 + 12,000 + 6,000 | **₹78,000** |
| PF | 12% × 60,000 | ₹7,200 |
| Tax | 10% × 78,000 (Gross in 50k–100k slab) | ₹7,800 |
| **Net Salary** | 78,000 − 7,200 − 7,800 | **₹63,000** |

**Part-Time, Basic = ₹40,000:**

| Step | Calculation | Amount |
| :--- | :--- | ---: |
| HRA | 20% × 40,000 | ₹8,000 |
| DA | 0% (Part-Time) | ₹0 |
| **Gross Salary** | 40,000 + 8,000 + 0 | **₹48,000** |
| PF | 12% × 40,000 | ₹4,800 |
| Tax | 0% (Gross ≤ 50,000) | ₹0 |
| **Net Salary** | 48,000 − 4,800 − 0 | **₹43,200** |

---

## Compile and Run

### Prerequisites
Java Development Kit (JDK) **8 or later** (tested on JDK 8 through JDK 26).

### Step 1: Navigate to project root
```bash
cd EmployeePayrollManagement
```

### Step 2: Create the bin output directory (first time only)
```bash
# Windows
mkdir bin

# macOS / Linux
mkdir -p bin
```

### Step 3: Compile
```bash
javac -d bin src/exceptions/*.java src/salary/*.java src/deduction/*.java src/employee/*.java src/payroll/*.java src/Main.java
```

### Step 4: Run the application
```bash
java -cp bin Main
```

### Step 5: Run the automated test suite
```bash
javac -d bin src/exceptions/*.java src/salary/*.java src/deduction/*.java src/employee/*.java src/payroll/*.java src/Main.java src/PayrollSystemTest.java
java -cp bin PayrollSystemTest
```

---

## Git Commit History

| Commit | Description |
| :--- | :--- |
| `8fef1e8` | Initial commit: Core Java employee payroll management system |
| `40b2185` | Fix employee ID and salary input validation |
| `0ae08b5` | Refactor common salary calculations into Employee base class |
| `6effae5` | Standardize custom exception constructors and serialVersionUID |
| `8a94bf6` | Add in-place employee record update in custom linked list |
| `06bdfae` | Add department search traversal to custom linked list |
| `e1bd88f` | Implement in-place bubble sort on custom singly linked list |
| `8621683` | Replace String.repeat with portable borders for Java 8 compatibility |
| `231c137` | Add defensive input validation to Allowance and Deduction models |
| `065f7bc` | Standardize ASCII table border alignment in Payslip view |
| `8142313` | Add financial summary aggregations to PayrollHistory Collections |
| `737059e` | Add batch payslip generation and summary view to PayrollManager |
| `9bca5f1` | Update console menu with confirmation prompts, employee update and sorting |
| `b6c2c32` | Add standalone automated regression test suite for payroll system |
| `(this)` | Complete README with viva preparation guide |

---

## Viva Preparation — Frequently Asked Questions

### Q1: Why did you use a custom Singly Linked List instead of Java's built-in `ArrayList` or `LinkedList`?

**Answer:** The purpose is to demonstrate Data Structures and Algorithms (DSA) from scratch. I implemented the `Node` class with a `data` field and a `next` pointer and manually coded insertion, traversal, search, in-place deletion, in-place update, department-based filtering, and bubble sort entirely using pointer manipulation. This is not possible or meaningful when using a built-in collection because the internal structure is hidden. The custom implementation proves that I understand how linked structures work at a fundamental level.

---

### Q2: If you already have a custom Linked List, why do you also use `ArrayList` for `PayrollHistory`?

**Answer:** They serve different purposes. The Linked List satisfies the DSA requirement for the employee registry — records need to be inserted, searched, updated, and deleted frequently. The `ArrayList<Payslip>` satisfies the Java Collections Framework requirement for the payroll audit log — payslip records are only ever added and read sequentially, so `ArrayList` is the right tool: it provides O(1) amortized appending, fast iteration for aggregation, and integrates naturally with `Collections.unmodifiableList()` for encapsulation. Using both deliberately demonstrates sound design judgment.

---

### Q3: Why is `Employee` an abstract class instead of an interface?

**Answer:** Because employees share both **state** (id, name, department, basicSalary) and **shared concrete behaviour** (calculateGrossSalary, calculateDeduction, calculateNetSalary). Interfaces in Java cannot hold instance variables, so sharing state through an interface is not possible. An abstract class lets me define the common fields and implement the shared calculation logic once, while still enforcing that every subclass provides its own allowance logic through the abstract `calculateAllowance()` method.

---

### Q4: What OOP concept is demonstrated when `employeeManager.getAllEmployees()` returns a list containing both `FullTimeEmployee` and `PartTimeEmployee` objects, and you call `calculateNetSalary()` on each?

**Answer:** This is **Runtime Polymorphism** (dynamic method dispatch). The variable is typed as `Employee`, but at runtime the JVM checks the actual object type and calls the correct overridden `calculateAllowance()` method — either the Full-Time version (HRA 20% + DA 10%) or the Part-Time version (HRA 20%, DA 0%). The salary calculation logic then flows correctly from the base class using the allowance returned by the subclass.

---

### Q5: Why are your custom exceptions checked exceptions rather than unchecked (RuntimeException)?

**Answer:** `DuplicateEmployeeException`, `InvalidSalaryException`, and `EmployeeNotFoundException` represent **expected, recoverable business errors** — not programming bugs. Using checked exceptions forces the caller (like `Main.java`) to explicitly write `try-catch` blocks and handle each failure gracefully with a user-friendly message. If they were unchecked, the compiler would not enforce handling and the errors could crash the application silently.

---

### Q6: Why did you use `double` instead of `BigDecimal` for salary amounts?

**Answer:** For this academic console project, `double` is appropriate. The values are formatted with `printf("%.2f")` throughout the application, which rounds to 2 decimal places on display. `BigDecimal` would be the correct choice in an enterprise or banking system to prevent IEEE-754 binary floating-point rounding errors (e.g., `0.1 + 0.2` ≠ `0.3` exactly). I am aware of this trade-off and can explain when each is appropriate.

---

### Q7: What is the time complexity of your Bubble Sort and can you improve it?

**Answer:** The worst-case time complexity of Bubble Sort is **O(n²)** — for each of the n passes, we compare up to n-1 adjacent pairs. For a small employee registry this is perfectly acceptable. The optimised version I implemented adds an early termination flag (`swapped`): if a full pass makes zero swaps, the list is already sorted and we stop immediately, giving a best-case complexity of **O(n)** for already-sorted input. For large datasets, Merge Sort on a linked list at **O(n log n)** would be a superior alternative.

---

### Q8: Explain the salary rules for Full-Time and Part-Time employees.

**Answer:**

| Component | Full-Time | Part-Time |
| :--- | :--- | :--- |
| HRA | 20% of Basic | 20% of Basic |
| DA | **10% of Basic** | **0%** |
| Gross | Basic + HRA + DA | Basic + HRA |
| PF | 12% of Basic | 12% of Basic |
| Tax | On Gross (slab) | On Gross (slab) |
| Net | Gross − PF − Tax | Gross − PF − Tax |

Tax slabs: Gross ≤ ₹50,000 → 0%; ₹50,001–₹1,00,000 → 10%; > ₹1,00,000 → 20%.
