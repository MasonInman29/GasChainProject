package GasChainPackage;

public interface PaymentProcessable {
    boolean arrangePayment(double amount);
    void recordTransaction(String transactionId, double amount);
}
