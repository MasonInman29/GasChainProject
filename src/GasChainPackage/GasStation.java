package GasChainPackage;

public class GasStation {
    private double balance;
    private final Bank bank;

    public GasStation(Bank bank) {
        this.balance = 0.0;
        this.bank = bank;
    }

    public void deposit(String paymentInfo, double amount) {
        bank.deposit(paymentInfo, amount);
        balance += amount;
        System.out.println("Station balance updated: $" + balance);
    }

    public double getBalance() {
        return balance;
    }

    //-------------------STORE------------------//

    public static int sellItem(int list){
        return 0;
    }

    public static int orderItems(){
        return 0;
    }
}