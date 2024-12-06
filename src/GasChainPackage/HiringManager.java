package GasChainPackage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HiringManager extends Employee {

    private static final String EMPLOYEE_DATABASE = "EmployeeDatabase.json";
    private static final String CANDIDATES_FILE = "candidates.json";
    private final Scanner scanner;

    public HiringManager(int employeeID, String name, String role) {
        super(employeeID, name, role);
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("===Hiring Manager Menu===");
            System.out.println("1. Hire Employees");
            System.out.println("2. Fire Employees");
            System.out.println("3. View Employees");
            System.out.println("9. Back to Previous Menu");

            try {
                int userChoice = scan.nextInt();
                scan.nextLine();

                switch (userChoice) {
                    case 1:
                        hireEmployee();
                        break;
                    case 2:
                        fireEmployee();
                        break;
                    case 3:
                        printEmployees();
                        break;
                    case 9:
                        running = false;
                        break;
                    default:
                        System.out.println("Error: Invalid option, ");
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid Input. Please enter a number.");
                scan.nextLine();
            }
        }
        return; //Return to main menu
    }
    // Hire an employee from the candidate pool
    public void hireEmployee() {
        System.out.println("\n=== Hire Employee ===");
        JSONArray candidates = FileUtility.loadJSONFromFile(CANDIDATES_FILE);

        if (candidates == null || candidates.length() == 0) {
            System.out.println("No candidates available.");
            return;
        }

        // Display candidates
        for (int i = 0; i < candidates.length(); i++) {
            JSONObject candidate = candidates.getJSONObject(i);
            System.out.println((i + 1) + ". " + candidate.getString("name") + " - Role: " + candidate.getString("role"));
        }
        System.out.println("0. Cancel");

        // Select a candidate
        System.out.print("Enter the number of the candidate to hire: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 0) {
            System.out.println("Operation cancelled.");
            return;
        }

        if (choice < 1 || choice > candidates.length()) {
            System.out.println("Invalid choice.");
            return;
        }

        // Add selected candidate to EmployeeDatabase
        JSONObject selectedCandidate = candidates.getJSONObject(choice - 1);

        try {
            FileUtility.addItemToJSON(selectedCandidate, EMPLOYEE_DATABASE);
            System.out.println("Successfully hired " + selectedCandidate.getString("name") + ".");
        } catch (Exception e) {
            System.out.println("Failed to hire employee: " + e.getMessage());
        }
    }

    // Fire an employee from the system
    public void fireEmployee() {
        System.out.println("\n=== Fire Employee ===");
        JSONArray employees = FileUtility.loadJSONFromFile(EMPLOYEE_DATABASE);

        if (employees == null || employees.length() == 0) {
            System.out.println("No employees in the database.");
            return;
        }

        // Display employees
        for (int i = 0; i < employees.length(); i++) {
            JSONObject employee = employees.getJSONObject(i);
            System.out.println((i + 1) + ". " + employee.getString("name") + " - Role: " + employee.getString("role"));
        }
        System.out.println("0. Cancel");

        // Select an employee to fire
        System.out.print("Enter the number of the employee to fire: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 0) {
            System.out.println("Operation cancelled.");
            return;
        }

        if (choice < 1 || choice > employees.length()) {
            System.out.println("Invalid choice.");
            return;
        }

        // Get the employee ID and remove them
        int employeeId = employees.getJSONObject(choice - 1).getInt("id"); // Assuming "id" is the unique identifier
        try {
            FileUtility.removeItemFromJSON(employeeId, EMPLOYEE_DATABASE);
            System.out.println("Employee successfully removed.");
        } catch (Exception e) {
            System.out.println("Error: Failed to remove employee: " + e.getMessage());
        }
    }

    public void printEmployees() {
        JSONArray employees = FileUtility.loadJSONFromFile(EMPLOYEE_DATABASE);

        if (employees == null || employees.length() == 0) {
            System.out.println("No employees in the database.");
            return;
        }

        // Display employees
        for (int i = 0; i < employees.length(); i++) {
            JSONObject employee = employees.getJSONObject(i);
            System.out.println((i + 1) + ". " + employee.getString("name") + " - Role: " + employee.getString("role"));
        }
    }


    public boolean writeToFile(String fileName, JSONObject jsonObject) throws IOException {
        try (FileWriter file = new FileWriter(fileName, true)) { // Append to file
            file.write(jsonObject.toString() + System.lineSeparator());
            return true;
        } catch (IOException e) {
            System.out.println("Failed to write to file: " + e.getMessage());
            return false;
        }
    }

}
