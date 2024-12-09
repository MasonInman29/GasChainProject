package GasChainPackage;

import java.util.List;
import java.util.Scanner;

public class Main {
    private Bank bank;
    private TransactionHandler transactionHandler;
    private GasStation station;
    private FuelPump fuelPump;
    private Employee employee;
    private Scanner scan;
    private SecuritySystem securitySystem;
    private JSONArray monitoringLogs;

    public Main() {
        bank = new Bank(1, 0.0);
        transactionHandler = new TransactionHandler();
        employee = new Employee(1, "John Doe", "Employee");
        station = new GasStation(1, "456 Station St", employee);
        fuelPump = new FuelPump(transactionHandler, bank, true);
        scan = new Scanner(System.in);
        securitySystem = new SecuritySystem();
        monitoringLogs = new JSONArray();
    }

    public static void main(String[] args) {
        Main mainProgram = new Main();
        mainProgram.run();
    }

    private void run() {
        System.out.println("Welcome to the Gas Station!");
        boolean running = true;

        while (running) {
            System.out.println("\nWhat is your title?");
            printMenu();

            try {
                int userChoice = scan.nextInt();
                scan.nextLine();

                switch (userChoice) {
                    case 1:
                        setCustomer();
                        break;
                    case 2:
                        employeeMenu();
                        break;
                    case 3:
                        managerMenu();
                        break;
                    case 9:
                        running = false;
                        break;
                    default:
                        System.out.println("Error: Invalid option");
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid Input. Please enter a number.");
                scan.nextLine();
            }
        }
    }

    private void printMenu() {
        System.out.println("1. Customer");
        System.out.println("2. Employee");
        System.out.println("3. Manager");
        System.out.println("9. Exit");
    }

    private void employeeMenu() {
        System.out.println("Hi Employee! What is your name?");
        String name = scan.nextLine();
        System.out.println("Hi " + name + "! What is your ID Number?");
        int idNum = scan.nextInt();
        scan.nextLine();

        if (idNum <= 0 || name.isEmpty()) {
            System.out.println("Invalid information, please try again.");
            return;
        }

        employee = new Employee(idNum, name, "Employee");
        employee.setStation(this.station);

        boolean running = true;
        while (running) {
            System.out.println("\n=== Employee Menu ===");
            System.out.println("1. Monitor Security Cameras");
            System.out.println("2. View  Monitoring Logs");
            System.out.println("9. Exit to Main Menu");

            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {
                case 1:
                    monitorSecurityCameras();
                    break;
                case 2:
                    viewLogs();
                case 9:
                    running = false;
                    break;
                default:
                    System.out.println("Error: Invalid option.");
            }
        }
    }

    private void managerMenu() {
        System.out.println("Hi Manager! What is your name?");
        String name = scan.nextLine();
        System.out.println("Hi " + name + "! What is your ID Number?");
        int idNum = scan.nextInt();
        scan.nextLine();

        if (idNum <= 0 || name.isEmpty()) {
            System.out.println("Invalid information, please try again.");
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\n=== Manager Menu ===");
            System.out.println("1. Monitor Security Cameras");
            System.out.println("2. Station Manager Options");
            System.out.println("3. View Monitoring Logs");
            System.out.println("9. Exit to Main Menu");

            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {
                case 1:
                    monitorSecurityCameras();
                    break;
                case 2:
                    setStationManager();
                    break;
                case 3:
                    viewLogs();
                case 9:
                    running = false;
                    break;
                default:
                    System.out.println("Error: Invalid option.");
            }
        }
    }

    private void monitorSecurityCameras() {
        System.out.println("\n--- Monitoring Security Cameras ---");
        List<SecurityCamera> cameras = securitySystem.getCameras();

        try {
            for (SecurityCamera camera : cameras) {
                camera.displayFeed();

                // Log the detailed monitoring event
                JSONObject logEntry = new JSONObject();
                logEntry.put("event", "Monitor Camera");
                logEntry.put("cameraId", camera.getCameraId());
                logEntry.put("location", camera.getLocation());
                logEntry.put("status", camera.isActive() ? "Active" : "Inactive");
                logEntry.put("timestamp", System.currentTimeMillis());

                monitoringLogs.put(logEntry);
            }

            System.out.println("Monitoring events logged successfully.");
        } catch (Exception e) {
            System.out.println("Error: An unexpected error occurred while monitoring cameras.");
            e.printStackTrace();
        }
    }

        private void viewLogs() {
            System.out.println("\n--- Monitoring Logs ---");
            if (monitoringLogs.length() == 0) {
                System.out.println("No logs available.");
            } else {
                for (int i = 0; i < monitoringLogs.length(); i++) {
                    System.out.println(monitoringLogs.get(i).toString());
                }
            }
        }

    private void setCustomer() {
        System.out.println("Hi Customer! What is your name?");
        String name = scan.nextLine();
        System.out.println("Good Bye, " + name + "! Have a great day!");
    }

    private void setStationManager() {
        System.out.println("Station Manager functionality is not yet implemented.");
    }

//        // Customer provides payment information
//        String paymentInfo = customer.providePaymentInfo();
//
//        // Assuming we have a way to determine the amount of gas purchased
//        double amount = 0;
//        boolean success = false;
//        try {
//            amount = parseInt(paymentInfo);
//            success = fuelPump.purchaseGas(paymentInfo, station, amount);
//        } catch (Exception e) {
//            System.out.println("An Error occurred while obtaining or processing payment.");
//        }
//
//        if (success) {
//            System.out.println("Thank you for your purchase!");
//        } else {
//            System.out.println("Transaction failed. Please try again.")
}

