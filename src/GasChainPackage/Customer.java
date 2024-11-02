package GasChainPackage;

import java.util.Scanner;

public class Customer {
    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    public String providePaymentInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter payment information for " + name + ": ");
        return scanner.nextLine();
    }
}
