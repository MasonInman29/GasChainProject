package GasChainPackage;

public class FuelSupplier implements OrderProcessable, DeliveryReceivable{
    @Override
    public boolean placeOrder(String fuelType, double quantity) {
        // Logic to process the order placed by the StationManager
        System.out.println("FuelSupplier received order for " + quantity + " units of " + fuelType);
        return true;
    }

    @Override
    public boolean confirmOrder(String orderId) {
        // Logic to confirm the order ID
        System.out.println("FuelSupplier confirming order ID: " + orderId);
        return true;
    }

    @Override
    public boolean verifyDelivery(String fuelType, double quantity) {
        // Logic to confirm the delivery before it is handed over to StationManager
        System.out.println("FuelSupplier verifying delivery for " + quantity + " units of " + fuelType);
        return true;
    }

    @Override
    public void recordDelivery(String fuelType, double quantity, String date) {
        // Logic to record delivery details for future reference
        System.out.println("FuelSupplier recording delivery of " + quantity + " units of " + fuelType + " on " + date);
    }
}