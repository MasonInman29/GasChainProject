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
                        purchaseItems();
                        break;
                    case 5:
                        findItem();
                        break;
                    case 6:
                        removeItem();
                        break;
                    case 7:
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

    private void purchaseItems() {
        System.out.println("Welcome inside the Gas Station!");
        boolean running = true;
        Map<Integer, Integer> myBag = new HashMap<>();

        while (running) {
            System.out.println("\nWhat would you like to purchase? Enter item ID or 0 to stop.");

            // Display the available stock
            printStock();

            try {
                int userChoice = scan.nextInt();
                scan.nextLine();

                switch (userChoice) {
                    case 0:
                        running = false;
                        break;
                    default:
                        boolean validInput =  addToBag(userChoice);
                        if (validInput) {
                            System.out.println("Item successfully added to the bag.");
                            // Check if the item is already in the bag and update its quantity
                            myBag.put(userChoice, myBag.getOrDefault(userChoice, 0) + 1);
                        } else {
                            System.out.println("Unable to add item. It might be out of stock.");
                        }
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid Input. Please enter a number.");
                scan.nextLine();
            }
        }
        System.out.println("Would you like to checkout or exit? c/e");
        char userChoice ;
        try {
            userChoice = scan.nextLine().charAt(0);
            if(userChoice == 'e' || userChoice == 'E') {
                return;
            } else if (userChoice == 'c' || userChoice == 'C'){
                System.out.print("How much are you paying? Sale Total ");
                System.out.println( getSalesTotal(myBag));
                double payment = scan.nextDouble();
                scan.nextLine(); // Clear the newline after nextDouble()
                System.out.println("Payment ammount: " + payment);
                completeSale(myBag, payment);
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid Input. Please enter c or e.");
        }
        System.out.println("\nThank you for comming into the Gas Station!");
    }

    private void findItem() {
        System.out.println("\n=== Find Item ===");

        // Get the current inventory from the station
        Map<String, Integer> currentInventory = station.getAllInventory();

        if (currentInventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        // Get search input from employee
        System.out.println("Enter search term (item name):");
        String searchTerm = scan.nextLine().trim().toLowerCase();

        // Track if we found any matches
        boolean foundMatch = false;
        System.out.println("\nSearch Results:");

        // Search through inventory
        for (Map.Entry<String, Integer> entry : currentInventory.entrySet()) {
            String itemName = entry.getKey().toLowerCase();
            if (itemName.contains(searchTerm)) {
                System.out.println("Item: " + entry.getKey());
                System.out.println("Quantity in Stock: " + entry.getValue());
                if (entry.getValue() <= station.getStoreInventory().getReorderThreshold()) {
                    System.out.println("WARNING: Stock is low!");
                }
                System.out.println("------------------------");
                foundMatch = true;
            }
        }

        if (!foundMatch) {
            System.out.println("No items found matching '" + searchTerm + "'");
            System.out.println("\nAvailable items:");
            for (String item : currentInventory.keySet()) {
                System.out.println("- " + item);
            }
        }
    }

    private void removeItem() {
        System.out.println("\n=== Remove Item ===");

        // Get the current inventory from the station
        Map<String, Integer> currentInventory = station.getAllInventory();

        if (currentInventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        // Display current inventory
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : currentInventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " units");
        }

        // Get item to remove
        System.out.println("\nEnter item name to remove:");
        String itemToRemove = scan.nextLine().trim();

        if (!currentInventory.containsKey(itemToRemove)) {
            System.out.println("Error: Item '" + itemToRemove + "' not found in inventory");
            return;
        }

        // Get quantity to remove
        System.out.println("Current quantity of " + itemToRemove + ": " +
                currentInventory.get(itemToRemove));
        System.out.println("Enter quantity to remove:");

        try {
            int quantityToRemove = scan.nextInt();
            scan.nextLine();

            if (quantityToRemove <= 0) {
                System.out.println("Error: Quantity must be greater than 0");
                return;
            }

            if (quantityToRemove > currentInventory.get(itemToRemove)) {
                System.out.println("Error: Cannot remove more items than available in inventory");
                return;
            }

            // Prepare lists for inventory update
            List<String> items = new ArrayList<>();
            List<Integer> quantities = new ArrayList<>();
            items.add(itemToRemove);
            quantities.add(-quantityToRemove); // Negative quantity for removal

            boolean updateSuccess = station.updateInventoryLevels(items, quantities);

            if (updateSuccess) {
                System.out.println("Successfully removed " + quantityToRemove + " units of " +
                        itemToRemove);
                System.out.println("Updated quantity: " +
                        station.getAllInventory().getOrDefault(itemToRemove, 0));
            } else {
                System.out.println("Error: Failed to update inventory");
                System.out.println("Would you like to retry? (Y/N)");
                String retry = scan.nextLine();
                if (retry.equalsIgnoreCase("Y")) {
                    removeItem();
                }
            }

        } catch (InputMismatchException e) {
            // Extension 4a: Handle invalid input
            System.out.println("Error: Please enter a valid number");
            scan.nextLine();
        } catch (Exception e) {
            // Extension 5a: Handle other system errors
            System.out.println("System error occurred: " + e.getMessage());
            System.out.println("Please try again later");
        }
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


    /**
     * Helper Functions
     */
    private void printMenu() {
        System.out.println("1. Purchase Gas");
        System.out.println("2. Stock Inventory");
        System.out.println("3. Manage Money");
        System.out.println("4. Purchase Items");
        System.out.println("5. Find Item");
        System.out.println("6. Remove Item");
        System.out.println("7. Exit");
    }
}
