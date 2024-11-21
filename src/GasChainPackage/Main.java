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

    private StationManager stationManager;

    private FuelSupplier fuelSupplier;

    public Main() {
        bank = new Bank(1, 0.0);
        transactionHandler = new TransactionHandler();
        employee = new Employee(1, "Jane Smith", "Manager");
        station = new GasStation(1, "456 Station St", employee);
        stationManager = new StationManager("Ahmed","iowaGasStation");
        fuelSupplier = new FuelSupplier();
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
                        MaintenanceRequestSystem();
                        break;
                    case 6:
                        performOrderAssessment();
                        break;
                    case 7:
                        performPurchaseProcess();
                        break;
                    case 8:
                        FireSafetySystem();
                        break;
                    case 9:
                        running = false;
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
    private void FireSafetySystem() {
        // Create fire extinguishers
        FireExtinguisher extinguisher1 = new FireExtinguisher("Pump 1", true, "2025-12-31");
        FireExtinguisher extinguisher2 = new FireExtinguisher("Storage Room", false, "2023-06-15");

        // Create actors
        FireSafetyInspector inspector = new FireSafetyInspector();
        Employee employee1 = new Employee(12,"Alice","Employee");
        Employee employee2 = new Employee(3798,"Bob","Employee");
        StationManager manager1 = new StationManager("John","iowaGasStation");
        StationManager manager2 = new StationManager("Sarah","iowaGasStation");

        // Conduct inspections
        inspector.conductInspection(extinguisher1);
        inspector.conductInspection(extinguisher2);

        // Conduct training sessions
        inspector.trainEmployees(Arrays.asList(employee1, employee2));
        inspector.trainStationManagers(Arrays.asList(manager1, manager2));

        // Conduct a fire drill
        inspector.conductFireDrill();
    }

    /**
     * Use Cases
     */
    private void MaintenanceRequestSystem() {
        // Create actors
        Employee employee = new Employee(81,"Mohammed","Employee");
        StationManager stationManager = new StationManager("Ahmed","IowaGasStation");
        MaintenanceTechnician technician = new MaintenanceTechnician("John Doe", "Mechanical");

        // Log a maintenance request
        MaintenanceRequest request = employee.logMaintenanceRequest("Fuel pump malfunction", "Pump 3", "Urgent");

        // Station Manager reviews and assigns task
        stationManager.reviewRequest(request);
        stationManager.assignTechnician(request);

        // Technician inspects and performs maintenance
        technician.inspectIssue(request);
        technician.performMaintenance(request);

        // Technician reports status to Station Manager
        technician.reportStatus(request);

        // Close the maintenance request
        stationManager.closeRequest(request);

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
     * New Use Cases
     */
    private void performInventoryCheck() {
        System.out.println("\n=== Inventory Check ===");
        double gasolineLevel = stationManager.checkFuelLevel("Gasoline");
        System.out.println("Gasoline level: " + gasolineLevel + " gallons");

        if (gasolineLevel < 500) {
            System.out.println("Fuel level is low. Consider ordering more fuel.");
        } else {
            System.out.println("Fuel level is sufficient.");
        }
    }

    private void performOrderAssessment() {
        System.out.println("\n=== Order Assessment ===");
        double gasolineLevel = stationManager.checkFuelLevel("Gasoline");

        if (gasolineLevel < 500) {
            System.out.println("Fuel level is low. Placing an order for 1000 gallons of gasoline.");
            boolean orderPlaced = stationManager.placeOrder("Gasoline", 1000);

            if (orderPlaced) {
                System.out.println("Order successfully placed.");
            } else {
                System.out.println("Failed to place order. Try again later.");
            }
        } else {
            System.out.println("Fuel level is sufficient. No order needed.");
        }
    }

    private void performPurchaseProcess() {
        System.out.println("\n=== Purchase Process ===");
        performInventoryCheck();
        performOrderAssessment();

        System.out.println("Preparing to receive delivery...");
        employee.prepareForDelivery();

        System.out.println("Receiving delivery...");
        boolean deliverySuccess = stationManager.receiveFuelDelivery("Gasoline", 1000);

        if (deliverySuccess) {
            System.out.println("Delivery successfully received and verified.");
            station.recordTransaction("Fuel delivery", 1000);
        } else {
            System.out.println("Delivery verification failed.");
        }
    }

    private void printMenu() {
        System.out.println("1. Purchase Gas");
        System.out.println("2. Stock Inventory");
        System.out.println("3. Manage Money");
        System.out.println("4. Purchase Items");
        System.out.println("5. RequestMaintenance");
        System.out.println("6. Order Assessment");
        System.out.println("7. Perform Purchase Process");
        System.out.println("8. FireSafetySystem");
        System.out.println("9. Exit");
    }
}

