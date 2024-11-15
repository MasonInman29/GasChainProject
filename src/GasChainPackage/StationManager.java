package GasChainPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StationManager implements OrderProcessable, InventoryManageable, DeliveryReceivable, PaymentProcessable {

    // Fields to store fuel inventory, order history, and transaction log
    private Map<String, Double> inventory;
    private List<String> orderHistory;
    private List<String> transactionLog;

    private FuelSupplier supplier;

    public StationManager() {
        // Initialize the inventory, order history, and transaction log
        inventory = new HashMap<>();
        orderHistory = new ArrayList<>();
        transactionLog = new ArrayList<>();
        supplier = new FuelSupplier();

        // Initial inventory setup
        inventory.put("Gasoline", 50.0);
        inventory.put("Diesel", 30.0);
    }


    @Override
    public double checkFuelLevel(String fuelType) {
        return inventory.getOrDefault(fuelType, 0.0);
    }

    @Override
    public void updateInventory(String fuelType, double quantity) {

    }

    @Override
    public boolean placeOrder(String fuelType, double quantity) {
        // Add order to the history log
        String orderDetails = "Order placed: " + quantity + " units of " + fuelType;
        orderHistory.add(orderDetails);
        System.out.println(orderDetails);

        // Simulate placing an order (e.g., contacting a supplier)
        return true;
    }

    @Override
    public boolean confirmOrder(String orderId) {
        // Confirm the order in the order history
        String confirmationDetails = "Order confirmed with ID: " + orderId;
        orderHistory.add(confirmationDetails);
        System.out.println(confirmationDetails);

        return true;
    }

    @Override
    public boolean verifyDelivery(String fuelType, double quantity) {
        // Verify delivery by comparing with the expected order quantity
        if (quantity > 0) {
            System.out.println("Delivery verified for " + quantity + " units of " + fuelType);
            return true;
        } else {
            System.out.println("Delivery verification failed for " + fuelType);
            return false;
        }
    }

    @Override
    public void recordDelivery(String fuelType, double quantity, String date) {
        // Update the inventory with delivered fuel
        inventory.put(fuelType, inventory.getOrDefault(fuelType, 0.0) + quantity);

        // Log the delivery in the order history
        String deliveryDetails = "Delivery recorded: " + quantity + " units of " + fuelType + " on " + date;
        orderHistory.add(deliveryDetails);
        System.out.println(deliveryDetails);
    }

    @Override
    public boolean arrangePayment(double amount) {
        // Simulate arranging payment (e.g., transferring funds)
        System.out.println("Arranging payment of $" + amount);

        // Assume payment is always successful for this implementation
        return true;
    }

    @Override
    public void recordTransaction(String transactionId, double amount) {
        // Log the transaction in the transaction log
        String transactionDetails = "Transaction recorded - ID: " + transactionId + ", Amount: $" + amount;
        transactionLog.add(transactionDetails);
        System.out.println(transactionDetails);
    }

    // Additional helper methods to view logs and data

    public void printInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Double> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " units");
        }
    }

    public void printOrderHistory() {
        System.out.println("\nOrder History:");
        for (String order : orderHistory) {
            System.out.println(order);
        }
    }

    public void printTransactionLog() {
        System.out.println("\nTransaction Log:");
        for (String transaction : transactionLog) {
            System.out.println(transaction);
        }
    }

    public boolean receiveFuelDelivery(String fuelType, double quantity) {
        System.out.println("StationManager is receiving fuel delivery...");

        // Verify delivery with FuelSupplier
        boolean deliveryVerified = supplier.verifyDelivery(fuelType, quantity);
        if (deliveryVerified) {
            // Record delivery and update inventory
            supplier.recordDelivery(fuelType, quantity, "2024-11-12");
            inventory.put(fuelType, inventory.getOrDefault(fuelType, 0.0) + quantity);
            System.out.println("Fuel delivery received and added to inventory: " + quantity + " units of " + fuelType);
            return true;
        } else {
            System.out.println("Fuel delivery verification failed. Delivery not accepted.");
            return false;
        }
    }
}
