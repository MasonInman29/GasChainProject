package GasChainPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuelSupplier implements OrderProcessable, DeliveryReceivable {

    // Fields to store order details and delivery records
    private Map<String, Double> orders; // Stores fuel type and quantity for orders
    private List<String> deliveryRecords; // Stores delivery logs

    public FuelSupplier() {
        // Initialize the orders map and delivery records list
        orders = new HashMap<>();
        deliveryRecords = new ArrayList<>();
    }

    @Override
    public boolean placeOrder(String fuelType, double quantity) {
        // Store the order details in the orders map
        orders.put(fuelType, orders.getOrDefault(fuelType, 0.0) + quantity);
        System.out.println("FuelSupplier received order for " + quantity + " units of " + fuelType);
        return true;
    }

    @Override
    public boolean confirmOrder(String orderId) {
        // Simulate confirming an order with the provided order ID
        String confirmationMessage = "Order confirmed with ID: " + orderId;
        deliveryRecords.add(confirmationMessage); // Add confirmation to delivery records
        System.out.println("FuelSupplier confirming order ID: " + orderId);
        return true;
    }

    @Override
    public boolean verifyDelivery(String fuelType, double quantity) {
        // Verify that the delivery matches the expected order quantity
        double expectedQuantity = orders.getOrDefault(fuelType, 0.0);
        if (expectedQuantity >= quantity) {
            System.out.println("FuelSupplier verified delivery of " + quantity + " units of " + fuelType);
            return true;
        } else {
            System.out.println("Delivery verification failed. Ordered " + expectedQuantity + ", received " + quantity);
            return false;
        }
    }

    @Override
    public void recordDelivery(String fuelType, double quantity, String date) {
        // Record the delivery details
        String deliveryDetails = "Delivery recorded: " + quantity + " units of " + fuelType + " on " + date;
        deliveryRecords.add(deliveryDetails); // Add delivery details to the log
        System.out.println(deliveryDetails);

        // Update order status by reducing the delivered quantity from the orders map
        orders.put(fuelType, orders.get(fuelType) - quantity);
    }

    // Additional helper methods to view logs and data

    public void printOrders() {
        System.out.println("\nCurrent Orders:");
        for (Map.Entry<String, Double> entry : orders.entrySet()) {
            System.out.println("Fuel Type: " + entry.getKey() + ", Quantity Ordered: " + entry.getValue() + " units");
        }
    }

    public void printDeliveryRecords() {
        System.out.println("\nDelivery Records:");
        for (String record : deliveryRecords) {
            System.out.println(record);
        }
    }
}
