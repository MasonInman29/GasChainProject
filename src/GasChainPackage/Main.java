package GasChainPackage;

import java.util.Scanner;

public class Main {
    private Bank bank;
    private TransactionHandler transactionHandler;
    private GasStation station;
    private FuelPump fuelPump;
    private Customer customer;
    private Scanner scan;

    public Main() {
        // Initialize system components
        bank = new Bank();
        transactionHandler = new TransactionHandler();
        station = new GasStation(bank);
        fuelPump = new FuelPump(transactionHandler, bank, true); // Assume the pump has fuel
        customer = new Customer("John Doe");
        scan = new Scanner(System.in);
    }

    public static void main(String[] args) {
        // Create an instance of Main to run the program
        Main mainProgram = new Main();
        mainProgram.run();
    }

    private void run() {
        System.out.println("Welcome to the Gas Station!");

        System.out.println("What would you like to do at the Gas Station?");
        printMenu();

        try {
            int userChoice = scan.nextInt();
            switch (userChoice) {
                case 1:
                    purchaseGas();
                    break;
                default:
                    System.out.println("Error: Improper Input");
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid Input. Please enter a number.");
        }

        // Display final station balance
        System.out.println("Station final balance: $" + station.getBalance());
    }

    /**
     * Use Cases
     */
    private void purchaseGas() {
        // Customer provides payment information
        String paymentInfo = customer.providePaymentInfo(); // Ensure this returns the correct payment info

        // Assuming we have a way to determine the amount of gas purchased
        double amount = 10.0; // This is just an example, replace with actual logic

        // Attempt to purchase gas
        boolean success = fuelPump.purchaseGas(paymentInfo, station, amount);

        if (success) {
            System.out.println("Thank you for your purchase!");
        } else {
            System.out.println("Transaction failed. Please try again.");
        }
    }


    /**
     * Helper Functions
     */
    private void printMenu() {
        System.out.println("\t1. Purchase Gas");
        // Add more options as development continues
    }
}
