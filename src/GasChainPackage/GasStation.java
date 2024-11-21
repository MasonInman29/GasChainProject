package GasChainPackage;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class GasStation {
    private int stationID;
    private StoreInventory storeInventory;
    private double balance;
    private StationManager stationManager;
    private FuelSupplier fuelSupplier;
    private final int bankID = 1;
    private Bank bank;
    private static final String FILE_PATH = "itemInformation.json";
  
    private String location;
    private Employee cashier;
    private List<String> fuelTypesOffered;
    private List<String> convenienceItems;
    private List<String> servicesOffered;
    
    private List<JSONObject> transactionLog;


    public GasStation(int stationID, String location, Employee cashier) {
        this.stationID = stationID;
        this.balance = 100;
        this.location = location;
        this.cashier = cashier;
        this.fuelTypesOffered = new ArrayList<>();
        this.convenienceItems = new ArrayList<>();
        this.servicesOffered = new ArrayList<>();
        this.storeInventory = new StoreInventory();
        this.transactionLog = new ArrayList<>();
        this.bank = new Bank(bankID, 0);
        this.stationManager = new StationManager("Ahmed","iowaGasStation");
        this.fuelSupplier = new FuelSupplier();
    }

    public void recordTransaction(String type, double amount) {
        JSONObject transaction = new JSONObject();
        transaction.put("type", type);
        transaction.put("amount", amount);
        transactionLog.add(transaction);
    }

    public List<JSONObject> getTransactionLog() {
        return new ArrayList<>(transactionLog);
    }

    public void recordDiscrepancy(double expectedAmount, double actualAmount, String notes) {
        JSONObject discrepancy = new JSONObject();
        discrepancy.put("type", "discrepancy");
        discrepancy.put("expected", expectedAmount);
        discrepancy.put("actual", actualAmount);
        discrepancy.put("notes", notes);
        discrepancy.put("timestamp", System.currentTimeMillis());
        transactionLog.add(discrepancy);
    }

    // Method to load JSON data from file
    private static JSONObject loadJSONFromFile() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            return( new JSONObject(content));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    // Method to write JSON data to file
    private static void writeJSONToFile(JSONObject itemInfo) {
        if (itemInfo == null) {
            System.out.println("Error: No data to write to file.");
            return;
        }
        // Write JSON data to file
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(itemInfo.toString(4)); // Indent with 4 spaces for readability
        } catch (Exception e) {
            System.out.println("Error: Unable to write to file at " + FILE_PATH);
            e.printStackTrace();
        }
    }

    public static boolean updateItemInJSON(int itemId, String keyToUpdate, Object newValue) {
        JSONObject allItems = loadJSONFromFile();
        if (allItems == null) {
            System.out.println("Error: Could not load JSON data.");
            return false;
        }

        // Access the items JSONObject
        JSONObject itemsObject = allItems.getJSONObject("items");

        // Find the target item by ID
        JSONObject targetItem = itemsObject.optJSONObject(String.valueOf(itemId));
        if (targetItem == null) {
            System.out.println("Error: Item with ID " + itemId + " not found.");
            return false;
        }

        // Update the specified key with the new value
        targetItem.put(keyToUpdate, newValue);
//        System.out.println("Updated item ID " + itemId + ": Set " + keyToUpdate + " to " + newValue);

        // Save the updated JSON back to the file
        writeJSONToFile(allItems);
        return true;
    }

    /**
     * deposits of money into a bank
     * @param paymentInfo
     * @param amount
     */
    public void deposit(String paymentInfo, double amount) {
        if (bank.deposit(paymentInfo, amount)) {
            balance += amount;
            recordTransaction("deposit", amount);
            System.out.println("Station balance updated: $" + balance);
        }
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

    public boolean depositToBank(double amount) {
        if (amount <= balance) {
            String paymentInfo = "Station Deposit - Amount: " + amount;
            if (bank.deposit(paymentInfo, amount)) {
                balance -= amount;
                recordTransaction("bank_deposit", amount);
                return true;
            }
        }
        return false;
    }
    public double getStoreFunds() {

        return balance;
    }

    public Map<String, Integer> getAllInventory() {
        return storeInventory.getStockLevels();
    }

    public boolean updateInventoryLevels(List<String> items, List<Integer> quantities) {
        try {
            boolean updateSuccess = storeInventory.updateStock(items, quantities);
            if (updateSuccess) {
                checkReorderNeeds(items, quantities);
            }
            return updateSuccess;
        } catch (Exception e) {
            System.out.println("Error updating inventory: " + e.getMessage());
            return false;
        }
    }

    private void checkReorderNeeds(List<String> items, List<Integer> quantities) {
        Map<String, Integer> currentInventory = storeInventory.getStockLevels();
        for (String item : items) {
            if (currentInventory.containsKey(item)) {
                int currentQuantity = currentInventory.get(item);
                if (currentQuantity <= storeInventory.getReorderThreshold()) {
                    storeInventory.reorder(item);
                }
            }
        }
    }

    public void addToTransactionLog(String logEntry) {
        JSONObject transaction = new JSONObject();
        transaction.put("entry", logEntry);
        transaction.put("timestamp", System.currentTimeMillis());
        transactionLog.add(transaction);
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public double getbalance() {
        return balance;
    }

    /**
     *
     * @param itemid
     * @return
     */
    public static boolean addToBag(int itemid) {
        Integer itemID = Integer.valueOf(itemid);
        JSONObject allItems = loadJSONFromFile(); //assume JSON is loaded correctly
        // Get the item
        JSONObject item = allItems.getJSONObject("items").getJSONObject(String.valueOf(itemID));

        // Get the quantity and check if the item is in stock
        int quantity = item.getInt("quantity");
        if (quantity <= 0) {
            System.out.println("Error: Item is out of stock.");
            return false;
        }

        // Update the quantity in the JSON object
        updateItemInJSON(item.getInt("id"), "quantity",  quantity - 1);
        System.out.println("Item " + item.getString("name") + " added to bag.");
        return true;
    }

    /**
     *
     */
    public static void printStock() {
        JSONObject itemInfo = loadJSONFromFile(); // Assume this method loads the JSON data correctly
        JSONObject itemsObject = itemInfo.getJSONObject("items");

        for (Object keyStr : itemsObject.keySet()){
            // Convert key to an integer
            int key = Integer.valueOf(String.valueOf(keyStr));

            JSONObject item = itemsObject.getJSONObject(String.valueOf(keyStr));
            int id = item.getInt("id");
            String name = item.getString("name");
            int quantity = item.getInt("quantity");

            // Print the item details
            System.out.println("ID: " + id + ", Name: " + name + ", Quantity: " + quantity);
        }
    }

    public static double getSalesTotal(Map<Integer, Integer> items) {
        JSONObject itemInfo = loadJSONFromFile(); // Load the JSON data
        JSONObject itemsArray = itemInfo.getJSONObject("items");
        double total = 0;

        // Iterate through the items in the provided map
        for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
            Integer id = entry.getKey(); // Get item ID from map
            Integer quantity = entry.getValue(); // Get the quantity from the map

            JSONObject item = itemsArray.getJSONObject(String.valueOf(id)); // Get the item from JSON by ID

            // Check if the item exists in the JSON file
            if (item != null) {
                double price = item.getDouble("price"); // Get price of the item
                total += price * quantity; // Add price * quantity to total
            } else {
                System.out.println("Error: Item with ID " + id + " not found.");
            }
        }

        return total;
    }

    public static void printBag(Map<Integer, Integer> myBag) {
        JSONObject itemInfo = loadJSONFromFile(); // Assume this method loads the JSON data correctly
        JSONObject itemsObject = itemInfo.getJSONObject("items");

        for (Object keyStr : myBag.keySet()){
            // Convert key to an integer
            int key = Integer.valueOf(String.valueOf(keyStr));

            JSONObject item = itemsObject.getJSONObject(String.valueOf(keyStr));
            int id = item.getInt("id");
            String name = item.getString("name");
            int quantity = item.getInt("quantity");

            // Print the item details
            System.out.println("ID: " + id + ", Name: " + name + ", Quantity: " + quantity);
        }
    }

    /**
     *
     * @param items
     * @param paymentAmount
     * @return
     */
    public static boolean completeSale(Map<Integer, Integer> items, double paymentAmount) {
        double total = getSalesTotal(items); // Get the total cost from the map of items

        if (total > paymentAmount) { // Check if payment is insufficient
            System.out.println("Error: Insufficient payment. Total: " + total + ", Paid: " + paymentAmount);
            for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
                stockItem(String.valueOf( entry.getKey()),  entry.getValue());
            }
            return false;
        }

        System.out.println("Sale completed successfully. Total: " + total + ", Paid: " + paymentAmount);
        System.out.println("Sales Items: " );
        printBag(items);
        System.out.println("\nChange Back: $" + (paymentAmount - total));
        return true;
    }

    public static void orderNewItem(){}

    /**
     * NOT YET IMPLEMENTED
     * @param itemID
     * @param orderAmount
     * @return
     */
    public static boolean stockItem(String itemID, int orderAmount){
        return true;
    }
}
