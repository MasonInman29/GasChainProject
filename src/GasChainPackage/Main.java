package GasChainPackage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Main {
    private Bank bank;
    private TransactionHandler transactionHandler;
    private GasStation station;
    private FuelPump fuelPump;
    private Employee employee;
    private Customer customer;
    private Scanner scan;

    public Main() {
        bank = new Bank(1, 0.0);
        transactionHandler = new TransactionHandler();
        employee = new Employee(1, "Jane Smith", "Manager");
        station = new GasStation(1, "456 Station St", employee);
        employee.setStation(station);
        station.setBank(bank);
        fuelPump = new FuelPump(transactionHandler, bank, true);
        customer = new Customer("John Doe");
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
            System.out.println("\nWhat would you like to do at the Gas Station?");
            printMenu();

            try {
                int userChoice = scan.nextInt();
                scan.nextLine();

                switch (userChoice) {
                    case 1:
                        purchaseGas();
                        break;
                    case 2:
                        handleInventory();
                        break;
                    case 3:
                        manageMoney();
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
     * Use Cases
     */
    private void purchaseGas() {
        // Customer provides payment information
        String paymentInfo = customer.providePaymentInfo();

        // Assuming we have a way to determine the amount of gas purchased
        double amount = 0;
        boolean success = false;
        try {
            amount = parseInt(paymentInfo);
            success = fuelPump.purchaseGas(paymentInfo, station, amount);
        } catch (Exception e) {
            System.out.println("An Error occurred while obtaining or processing payment.");
        }

        if (success) {
            System.out.println("Thank you for your purchase!");
        } else {
            System.out.println("Transaction failed. Please try again.");
        }
    }

    private void handleInventory() {
        System.out.println("\n=== Inventory Management ===");

        // 1. Employee initiates stock check
        employee.stockItems();

        // Get input for inventory update
        try {
            System.out.println("\nEnter number of items to update:");
            int itemCount = scan.nextInt();
            scan.nextLine();

            List<String> items = new ArrayList<>();
            List<Integer> quantities = new ArrayList<>();

            for (int i = 0; i < itemCount; i++) {
                System.out.println("Enter item name:");
                items.add(scan.nextLine());

                System.out.println("Enter quantity:");
                quantities.add(scan.nextInt());
                scan.nextLine();
            }

            // 3. Employee updates inventory through station
            if (employee.updateInventory(items, quantities)) {
                System.out.println("Inventory updated successfully!");
            } else {
                System.out.println("Failed to update inventory. Please try again.");
            }

        } catch (Exception e) {
            System.out.println("Error: Invalid input. Please try again.");
            scan.nextLine();
        }
    }

    private void manageMoney() {
        System.out.println("\n=== Money Management ===");

        System.out.println("Previous Transaction History:");
        List<JSONObject> transactionLog = station.getTransactionLog();
        for (JSONObject transaction : transactionLog) {
            System.out.println(transaction);
        }

        double expectedAmount = station.getStoreFunds();
        System.out.println("\nCurrent Station Balance: $" + expectedAmount);
        System.out.println("Please perform physical count...");
        System.out.println("Enter physical count amount:");
        double physicalCount = scan.nextDouble();
        scan.nextLine();

        // Check if amounts match
        if (Math.abs(physicalCount - expectedAmount) > 0.01) {
            // 1a. Handle discrepancy
            System.out.println("Discrepancy detected!");
            System.out.println("Would you like to recount? (Y/N)");
            String recount = scan.nextLine();

            if (recount.equalsIgnoreCase("Y")) {
                System.out.println("Enter new physical count:");
                physicalCount = scan.nextDouble();
                scan.nextLine();
            }

            System.out.println("Enter notes about discrepancy:");
            String notes = scan.nextLine();
            station.addToTransactionLog("Discrepancy noted: Expected $" + expectedAmount +
                    ", Counted $" + physicalCount + " - Notes: " + notes);
        }

        // 2. Initiate deposit
        System.out.println("\nInitiate deposit of $" + physicalCount + "? (Y/N)");
        if (scan.nextLine().equalsIgnoreCase("Y")) {
            // 3. Process deposit
            if (bank.isOpen()) {
                // Attempt deposit
                if (station.depositToBank(physicalCount)) {
                    // 4. Confirm deposit
                    System.out.println("Deposit confirmed!");
                    // 5. Update transaction log
                    station.addToTransactionLog("Bank deposit completed: $" + physicalCount);
                } else {
                    System.out.println("Deposit failed. Please try again.");
                }
            } else {
                // 3a. Handle bank closed scenario
                System.out.println("Bank is currently closed.");
                System.out.println("Deposit request held. Please secure physical funds.");
                station.addToTransactionLog("Deposit pending (Bank closed): $" + physicalCount);
            }
        }
    }


    /**
     * Helper Functions
     */
    private void printMenu() {
        System.out.println("1. Purchase Gas");
        System.out.println("2. Stock Inventory");
        System.out.println("3. Manage Money");
        System.out.println("4. Exit");
    }
}
