package GasChainPackage;

public class FuelPump {
    private boolean hasFuel;
    private final TransactionHandler transactionHandler;
    private final Bank bank;

    public FuelPump(TransactionHandler transactionHandler, Bank bank, boolean hasFuel) {
        this.transactionHandler = transactionHandler;
        this.bank = bank;
        this.hasFuel = hasFuel;
    }

    public boolean purchaseGas(String paymentInfo, GasStation station, double amount) {
        if (!hasFuel) {
            System.out.println("FuelPump has no fuel.");
            return false;
        }
        if (transactionHandler.processPayment(paymentInfo, station, amount)) {
            System.out.println("FuelPump is enabled to dispense fuel.");
            return true;
        } else {
            System.out.println("Payment failed. FuelPump is not dispensing fuel.");
            return false;
        }
    }
}