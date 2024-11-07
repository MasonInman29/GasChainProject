package GasChainPackage;

public interface DeliveryReceivable {
    boolean verifyDelivery(String fuelType, double quantity);
    void recordDelivery(String fuelType, double quantity, String date);
}
