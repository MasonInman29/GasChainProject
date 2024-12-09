package GasChainPackage;

import java.util.Scanner;

public class SecuritySystemRunner {
    private static SecurityAlarm securityAlarm = new SecurityAlarm();
    private static Scanner scan = new Scanner(System.in);
    private static Employee employee;

    // Static method to run the security system
    public static void run(Employee emp) {
        employee = emp; // Set the employee instance
        System.out.println("Welcome to the Gas Station Security System, " + employee.name + "!");

        boolean running = true;
        while (running) {
            System.out.println("\nWhat would you like to do?");
            printMenu();

            try {
                int choice = scan.nextInt();
                scan.nextLine(); // Clear buffer

                switch (choice) {
                    case 1:
                        activateAlarm();
                        break;
                    case 2:
                        deactivateAlarm();
                        break;
                    case 3:
                        triggerAlarm();
                        break;
                    case 4:
                        viewAlarmLog();
                        break;
                    case 5:
                        running = false;
                        System.out.println("Goodbye, " + employee.name + "!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scan.nextLine();
            }
        }
    }

    // Print menu options
    private static void printMenu() {
        System.out.println("1. Activate Security Alarm");
        System.out.println("2. Deactivate Security Alarm");
        System.out.println("3. Trigger Alarm for Security Threat");
        System.out.println("4. View Alarm Log");
        System.out.println("5. Exit");
    }

    // Activate the alarm
    private static void activateAlarm() {
        securityAlarm.activateAlarm(employee.name);
    }

    // Deactivate the alarm
    private static void deactivateAlarm() {
        securityAlarm.deactivateAlarm(employee.name);
    }

    // Trigger the alarm
    private static void triggerAlarm() {
        System.out.println("Enter the reason for triggering the alarm:");
        String reason = scan.nextLine();
        securityAlarm.triggerAlarm(reason);
    }

    // View alarm logs
    private static void viewAlarmLog() {
        securityAlarm.viewLog();
    }
}
