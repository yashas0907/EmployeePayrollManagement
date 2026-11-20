package employee;

import exceptions.DuplicateEmployeeException;
import exceptions.EmployeeNotFoundException;
import exceptions.InvalidSalaryException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages employee records using a custom Singly Linked List Data Structure from scratch.
 * Satisfies Faculty DSA Requirement:
 * - Implements custom Node structure
 * - Insertion (at tail to preserve order)
 * - Traversal (for display and search)
 * - Search by ID (linear search O(n))
 * - Deletion by ID (unlinking node O(n))
 */
public class EmployeeManager {

    /**
     * Internal Node class representing an element in the Singly Linked List.
     */
    private static class Node {
        Employee data;
        Node next;

        Node(Employee data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public EmployeeManager() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Adds an employee to the custom singly linked list.
     * Validates employee ID, basic salary, and checks for duplicate IDs.
     */
    public void addEmployee(Employee emp) throws DuplicateEmployeeException, InvalidSalaryException {
        if (emp == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }
        if (emp.getId() <= 0) {
            throw new IllegalArgumentException("Employee ID must be a strictly positive integer (> 0). Provided: " + emp.getId());
        }
        if (emp.getBasicSalary() <= 0 || Double.isNaN(emp.getBasicSalary()) || Double.isInfinite(emp.getBasicSalary())) {
            throw new InvalidSalaryException("Basic salary must be finite and strictly greater than 0. Provided: " + emp.getBasicSalary());
        }
        if (searchEmployee(emp.getId()) != null) {
            throw new DuplicateEmployeeException("Employee with ID " + emp.getId() + " already exists.");
        }

        Node newNode = new Node(emp);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    /**
     * Searches for an employee by ID by traversing the linked list.
     * Returns the Employee object if found, otherwise returns null.
     */
    public Employee searchEmployee(int id) {
        if (id <= 0) {
            return null;
        }
        Node current = head;
        while (current != null) {
            if (current.data.getId() == id) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Gets an employee by ID or throws EmployeeNotFoundException if not present.
     */
    public Employee getEmployeeById(int id) throws EmployeeNotFoundException {
        if (id <= 0) {
            throw new EmployeeNotFoundException("Invalid Employee ID: " + id + ". ID must be a strictly positive integer (> 0).");
        }
        Employee emp = searchEmployee(id);
        if (emp == null) {
            throw new EmployeeNotFoundException("Employee with ID " + id + " was not found.");
        }
        return emp;
    }

    /**
     * Removes an employee with the given ID from the singly linked list.
     * Throws EmployeeNotFoundException if the employee does not exist.
     */
    public boolean removeEmployee(int id) throws EmployeeNotFoundException {
        if (id <= 0) {
            throw new EmployeeNotFoundException("Invalid Employee ID: " + id + ". ID must be a strictly positive integer (> 0).");
        }
        if (head == null) {
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found (Employee list is empty).");
        }

        // If head node itself holds the employee to be deleted
        if (head.data.getId() == id) {
            head = head.next;
            size--;
            return true;
        }

        Node prev = head;
        Node current = head.next;

        while (current != null) {
            if (current.data.getId() == id) {
                prev.next = current.next;
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }

        throw new EmployeeNotFoundException("Employee with ID " + id + " was not found for removal.");
    }

    /**
     * Displays all employees by traversing the linked list.
     */
    public void displayAllEmployees() {
        if (head == null) {
            System.out.println("\n[!] No employees found in the system.");
            return;
        }

        System.out.println("\n" + "=".repeat(75));
        System.out.printf("%-5s | %-16s | %-12s | %-10s | %10s\n",
                "ID", "Name", "Department", "Type", "Basic (Rs.)");
        System.out.println("-".repeat(75));

        Node current = head;
        while (current != null) {
            Employee e = current.data;
            System.out.printf("%-5d | %-16s | %-12s | %-10s | %10.2f\n",
                    e.getId(), e.getName(), e.getDepartment(), e.getEmployeeType(), e.getBasicSalary());
            current = current.next;
        }
        System.out.println("=".repeat(75));
        System.out.println("Total Employees: " + size);
    }

    /**
     * Returns a standard Java List of all employees (useful for iterations or payroll processing).
     */
    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        Node current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }
}
