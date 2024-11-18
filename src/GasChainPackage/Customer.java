package GasChainPackage;

import java.util.Scanner;

public class Customer {
    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String providePaymentInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter payment information for " + name + ": ");
        System.out.print("Enter how many dollars of gas would you like? ");
        return scanner.nextLine();
    }
}
