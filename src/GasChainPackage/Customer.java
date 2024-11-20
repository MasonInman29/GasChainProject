package GasChainPackage;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static GasChainPackage.GasStation.*;
import static java.lang.Integer.parseInt;

public class Customer {
    private final String name;
    private Bank bank;
    private TransactionHandler transactionHandler;
    private GasStation station;
    private FuelPump fuelPump;
    private Scanner scan;
    private Map<Integer, Integer> myBag = new HashMap<>();

    public Customer(String name) {
        this.name = name;
        transactionHandler = new TransactionHandler();
        bank = new Bank(1, 0.0);
        fuelPump = new FuelPump(transactionHandler, bank, true);
        this.scan = new Scanner(System.in);

        Customer customer = this;
        customer.run();
    }

    public String getName() {

        return name;
    }

    private void run() {
        System.out.println("Welcome to the Gas Station! " + name);
        boolean running = true;

        while (running) {
            System.out.println("\nWhat would you like to do at the Gas Station?");
            printMenu();

            try {
                System.out.println("IN TRY");
                int userChoice = scan.nextInt();
                scan.nextLine();
                System.out.println("UC:_" + userChoice + ".");

                switch (userChoice) {
                    case 1:
                        purchaseGas();
                        break;
                    case 2:
                        addToBag();
                        break;
                    case 3:
//                        manageMoney();
                        break;
                    case 4:
//                        purchaseItems();
                        break;
                    case 5:
//                        findItem();
                        break;
                    case 6:
//                        removeItem();
                        break;
                    case 7:
                        running = false;
                        break;
                    default:
                        System.out.println("Error: Invalid option");
                        scan.nextLine();
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
        System.out.println("1. Purchase Gas");
        System.out.println("2. Add Items To Bag");
        System.out.println("3. ");
//        System.out.println("4. Purchase Items");
//        System.out.println("5. Find Item");
//        System.out.println("6. Remove Item");
        System.out.println("7. Exit");
    }

    public String providePaymentInfo() {
        System.out.println("Enter payment information for " + name + ": ");
        System.out.print("Enter how many dollars of gas would you like? ");
        return scan.nextLine();
    }

    private void purchaseGas() {
        // Customer provides payment information
        String paymentInfo = providePaymentInfo();

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

    private void addToBag(){
        System.out.println("Welcome to the Shop!");
        boolean running = true;
//        Map<Integer, Integer> myBag = new HashMap<>();

        while (running) {
            System.out.println("What would you like to purchase? Enter item ID or 0 to stop.");
            station.printStock();
            try {
                System.out.println("IN TRY");
                int userChoice = scan.nextInt();
                scan.nextLine();

                switch (userChoice) {
                    case 0:
                        running = false;
                        break;
                    default:
                        boolean validInput =  GasStation.addToBag(userChoice);
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
            if (userChoice == 'e' || userChoice == 'E') {
                return;
            } else if (userChoice == 'c' || userChoice == 'C')
                purchaseItems();
        } catch (Exception e) {
            System.out.println("Error: Invalid Input. Please enter c or e.");
        }
    }

    private void purchaseItems() {
        Rewards rewards = null;
        int rewardsAmount = 0;
        System.out.print("Your total today is: $" + getSalesTotal(myBag));

        System.out.println("Will you be using rewards today? y/n");
        char userChoice = scan.nextLine().charAt(0);
        if(userChoice == 'y' || userChoice == 'Y') {
            System.out.println("How many points do you currently have?");
            int points = scan.nextInt();
            rewards = new Rewards(points);
            System.out.println("You have " + rewards + " rewards points!\nHow many points do you like to use?");
            rewardsAmount = scan.nextInt();    /**
             * Use Cases
             */

            while(rewardsAmount > points){
                System.out.println("Insufficient ammount. Enter a number less than " + points );
            }
//          REWARDS: update rewards ammount
            myBag.put(-1, Integer.valueOf(station.useRewards( getSalesTotal(myBag), rewardsAmount, points)));
        }
        else{
            System.out.println("Would you like to start a new rewards card? y/n");
            char userChoiceStartRewards = scan.nextLine().charAt(0);
            if(userChoiceStartRewards == 'y' || userChoiceStartRewards == 'Y') {
                rewards = new Rewards();
                System.out.println("New Rewards Card! $" + rewards.getRewards());
            }
            rewards = new Rewards();
        }


        System.out.print("How much are you paying? Sale Total ");
        System.out.println( getSalesTotal(myBag));
        double payment = scan.nextDouble();
        scan.nextLine(); // Clear the newline after nextDouble()
        System.out.println("Payment ammount: " + payment);
        //CHANGE: complete with or without rewards????
        if ( station.completeSale(myBag, payment, rewards, rewardsAmount) ){
            System.out.println("\nThank you for your purchase!");
            return;
        }
        System.out.println("Purchase Failed.");
    }

}

