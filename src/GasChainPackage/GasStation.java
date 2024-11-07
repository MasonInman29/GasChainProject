package GasChainPackage;

public class GasStation {
    private double balance;
    private final Bank bank;
    private StationManager stationManager;
    private FuelSupplier fuelSupplier;

    public GasStation(Bank bank) {
        this.balance = 0.0;
        this.bank = bank;
        this.stationManager = new StationManager();
        this.fuelSupplier = new FuelSupplier();
    }

    public void deposit(String paymentInfo, double amount) {
        bank.deposit(paymentInfo, amount);
        balance += amount;
        System.out.println("Station balance updated: $" + balance);
    }

    public void performPurchaseProcess() {
        // Inventory check
        if (stationManager.checkFuelLevel("Gasoline") < 1000) {
            stationManager.placeOrder("Gasoline", 1000);
            fuelSupplier.confirmOrder("ORDER123");

            // Prepare for delivery
            stationManager.recordDelivery("Gasoline", 1000, "2024-11-07");

            // Arrange payment
            stationManager.arrangePayment(3000.0);
            stationManager.recordTransaction("TXN789", 3000.0);
        }
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