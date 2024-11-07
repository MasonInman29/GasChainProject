package GasChainPackage;

public interface OrderProcessable {
    boolean placeOrder(String fuelType, double quantity);
    boolean confirmOrder(String orderId);
}
