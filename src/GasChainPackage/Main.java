package GasChainPackage;


import java.util.*;

import static GasChainPackage.GasStation.*;
import static java.lang.Integer.parseInt;

public class Main {
    private Bank bank;
    private TransactionHandler transactionHandler;
    private GasStation station;
    private FuelPump fuelPump;
    private Employee employee;
//    private Customer customer;
    private Scanner scan;
    private Employee stationMannagerEMP;
    private StationManager stationManager;
    private FuelSupplier fuelSupplier;


    public Main() {
        bank = new Bank(1, 0.0);
        transactionHandler = new TransactionHandler();
        stationMannagerEMP = new Employee(1, "Jane Smith", "Manager");
        station = new GasStation(1, "456 Station St", stationMannagerEMP);
        stationManager = new StationManager();
        fuelSupplier = new FuelSupplier();
//        employee.setStation(station);
        station.setBank(bank);
        fuelPump = new FuelPump(transactionHandler, bank, true);
//        customer = new Customer("John Doe");
        scan = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Main mainProgram = new Main();
        mainProgram.run();
    }

    private void run() {
        System.out.println("Welcome to the Gas Station!");
        boolean running = true;

        while (running) {
            System.out.println("\nWhat is your title?");  //What are you here to do?
            printMenu();

            try {
                int userChoice = scan.nextInt();
                scan.nextLine();

                switch (userChoice) {
                    case 1:
                        setCustomer();
                        break;
                    case 2:
                        setEmployee();
                        break;
                    case 3:
                        managerMenu();
                        break;
                    case 4:
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

    /**
     * Helper Functions
     */
    private void printMenu() {
        System.out.println("1. Customer");
        System.out.println("2. Employee");
        System.out.println("3. Manager");
        System.out.println("4. Exit");
    }

    private void managerMenu() {
        boolean running = true;
        while (running) {
            System.out.println("===Manager Selection Menu===");
            System.out.println("Which Type of Manager are you?");
            System.out.println("1. Station Manager");
            System.out.println("2. Hiring Manager");
            System.out.println("9. Back to Previous Menu");

            try {
                int userChoice = scan.nextInt();
                scan.nextLine();

                switch (userChoice) {
                    case 1:
                        setStationManager();
                        break;
                    case 2:
                        setHiringManager();
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
    private void setCustomer(){
        System.out.println("Hi Customer! What is your name?");
        String name = scan.nextLine();
//        scan.nextLine();
        System.out.println("NAME: " + name);
        Customer newCustomer = new Customer(name, station);
        System.out.println("Good Bye, " + name + "! Have a great day!");
    }

    private void setEmployee() {
        System.out.println("Hi Employee! What is your name?");
        String name = scan.nextLine();
        System.out.println("Hi " + name + "! What is your ID Number?");
        int idNum = scan.nextInt();
        if (!(idNum > 0) && name.length() < 1) {
            System.out.println("Invalid information, please try again.");
        }

        employee = new Employee(idNum, name, "Employee");
        station = new GasStation(1, "456 Station St", employee);
        employee.setStation(this.station);
        employee.run();

        System.out.println("Good Bye, " + name + "! Have a great day!");
    }

    private void setStationManager() {
        System.out.println("Hi Employee! What is your name?");
        String name = scan.nextLine();
        System.out.println("Hi " + name + "! What is your ID Number?");
        int idNum = scan.nextInt();
        scan.nextLine(); // Clear buffer
        
        if (!(idNum > 0) && name.length() < 1) {
            System.out.println("Invalid information, please try again.");
            return;
        }
    
        stationManager = new StationManager();
        boolean running = true;
    
        while (running) {
            System.out.println("\n=== Station Manager Menu ===");
            System.out.println("1. Manage Promotional Campaign");
            System.out.println("2. View Inventory");
            System.out.println("3. View Order History");
            System.out.println("4. View Transaction Log");
            System.out.println("5. View Security Monitoring Logs");
            System.out.println("9. Exit");
    
            try {
                int choice = scan.nextInt();
                scan.nextLine(); // Clear buffer
    
                switch (choice) {
                    case 1:
                        stationManager.managePromotionalCampaign();
                        break;
                    case 2:
                        stationManager.printInventory();
                        break;
                    case 3:
                        stationManager.printOrderHistory();
                        break;
                    case 4:
                        stationManager.printTransactionLog();
                        break;
                    case 5:
                       employee.viewLogs();
                       break;
                    case 9:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid input");
                scan.nextLine(); // Clear buffer
            }
        }
    }

    private void setHiringManager() {
        final String EXAMPLE_USER_CREDENTIALS = "test";
        final String EXAMPLE_USER_PASSWORD = "test";
        final String HIRING_MANAGER_DEAFALT_NAME = "Hiring Manager";
        final int HIRING_MANAGER_ID = 999888777;

        System.out.println("===Hiring Manager Login===");
        System.out.println("Please enter Username: ");
        String username = scan.nextLine();
        System.out.println("Please enter Password: ");
        String password = scan.nextLine();

        if (username.equals(EXAMPLE_USER_CREDENTIALS) &&
            password.equals(EXAMPLE_USER_PASSWORD)) {
            System.out.println("Login Successful.");
            HiringManager hiringManager = new HiringManager(HIRING_MANAGER_ID, HIRING_MANAGER_DEAFALT_NAME, "Hiring Manager");
            station = new GasStation(1, "456 Station St", employee);
            hiringManager.setStation(this.station);
            hiringManager.run();
            System.out.println("Exiting to Main Menu.");
        } else {
            System.out.println("Login Failed. Exiting to Main menu.");
        }
        return; //Return to main menu
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

