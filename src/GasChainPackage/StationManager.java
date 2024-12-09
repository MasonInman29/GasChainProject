package GasChainPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StationManager implements OrderProcessable, InventoryManageable, DeliveryReceivable, PaymentProcessable {

    // Fields to store fuel inventory, order history, and transaction log
    private Map<String, Double> inventory;
    private List<String> orderHistory;
    private List<String> transactionLog;
    
    private static final String PROMOTIONS_FILE = "promotions.json";
    private static final String INVENTORY_FILE = "itemInformation.json";

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

    public void managePromotionalCampaign() {
        System.out.println("\n=== Manage Promotional Campaign ===");
        Scanner scanner = new Scanner(System.in);

        try {
            // Get current inventory and validation
            JSONArray inventory = FileUtility.loadJSONFromFile(INVENTORY_FILE);
            if (inventory == null || inventory.length() == 0) {
                System.out.println("Cannot access inventory data");
                return;
            }

            // Display current inventory with prices
            System.out.println("\nCurrent Inventory and Prices:");
            Map<String, JSONObject> itemMap = new HashMap<>();
            for (int i = 0; i < inventory.length(); i++) {
                JSONObject item = inventory.getJSONObject(i);
                itemMap.put(item.getString("name").toLowerCase(), item);
                System.out.printf("%s - Price: $%.2f, Stock: %d%n",
                        item.getString("name"),
                        item.getDouble("price"),
                        item.getInt("quantity"));
            }

            // Get item for promotion
            System.out.println("\nEnter item name for promotion:");
            String itemName = scanner.nextLine().trim();
            
            // Case-insensitive item lookup
            JSONObject selectedItem = itemMap.get(itemName.toLowerCase());
            if (selectedItem == null) {
                System.out.println("Error: Item not found in inventory");
                return;
            }

            // Get promotion details
            System.out.println("\nEnter discount percentage (0-100):");
            double discountPercent = scanner.nextDouble();
            if (discountPercent <= 0 || discountPercent >= 100) {
                System.out.println("Invalid discount percentage");
                return;
            }

            System.out.println("Enter promotion duration in days:");
            int duration = scanner.nextInt();
            if (duration <= 0) {
                System.out.println("Duration must be positive");
                return;
            }

            System.out.println("Enter quantity limit (0 for no limit):");
            int quantityLimit = scanner.nextInt();
            if (quantityLimit < 0) {
                System.out.println("Quantity limit cannot be negative");
                return;
            }

            // Create promotion record
            JSONObject promotion = createPromotionRecord(
                selectedItem, discountPercent, duration, quantityLimit);

            // Save promotion and update prices
            if (savePromotionAndUpdatePrices(promotion, selectedItem)) {
                System.out.println("\nPromotion created successfully!");
                System.out.println("Item: " + selectedItem.getString("name"));
                System.out.println("Discount: " + discountPercent + "%");
                System.out.println("Duration: " + duration + " days");
                if (quantityLimit > 0) {
                    System.out.println("Quantity limit: " + quantityLimit);
                }
                
                // Record transaction
                recordTransaction("PROMO_" + System.currentTimeMillis(), 
                                selectedItem.getDouble("price") * (discountPercent / 100.0));
            } else {
                System.out.println("Failed to create promotion");
            }

        } catch (Exception e) {
            System.out.println("Error in promotion management: " + e.getMessage());
        }
    }

    private JSONObject createPromotionRecord(JSONObject item, double discountPercent, 
                                        int duration, int quantityLimit) {
        JSONObject promotion = new JSONObject();
        promotion.put("itemName", item.getString("name"));
        promotion.put("originalPrice", item.getDouble("price"));
        promotion.put("discountPercent", discountPercent);
        promotion.put("startDate", System.currentTimeMillis());
        promotion.put("endDate", System.currentTimeMillis() + (duration * 24 * 60 * 60 * 1000L));
        promotion.put("quantityLimit", quantityLimit);
        promotion.put("status", "active");
        return promotion;
    }

    private boolean savePromotionAndUpdatePrices(JSONObject promotion, JSONObject item) {
        try {
            // Load existing promotions
            JSONArray promotions = FileUtility.loadJSONFromFile(PROMOTIONS_FILE);
            if (promotions == null) {
                promotions = new JSONArray();
            }
            
            // Add new promotion
            promotions.put(promotion);
            FileUtility.writeJSONToFile(promotions, PROMOTIONS_FILE);

            // Update item price
            double newPrice = item.getDouble("price") * 
                            (1 - promotion.getDouble("discountPercent") / 100.0);
            item.put("price", newPrice);
            
            // Get full inventory to update the item
            JSONArray inventory = FileUtility.loadJSONFromFile(INVENTORY_FILE);
            for (int i = 0; i < inventory.length(); i++) {
                JSONObject invItem = inventory.getJSONObject(i);
                if (invItem.getString("name").equals(item.getString("name"))) {
                    invItem.put("price", newPrice);
                    break;
                }
            }
            
            FileUtility.writeJSONToFile(inventory, INVENTORY_FILE);
            return true;

        } catch (Exception e) {
            System.out.println("Error saving promotion: " + e.getMessage());
            return false;
        }
    }
}
