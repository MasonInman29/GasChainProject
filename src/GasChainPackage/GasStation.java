package GasChainPackage;


import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Dictionary;


public class GasStation {
    private int stationID;
    private StoreInventory storeInventory;
    private double balance;
    private StationManager stationManager;
    private FuelSupplier fuelSupplier;
    private final int bankID = 1;
    private Bank bank;
    private static final String FILE_PATH = "../itemInformation.json";
  
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
        this.stationManager = new StationManager();
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

//    // Method to update the quantity of an item by ID
//    private static void updateItemQuantity(int itemId, int newQuantity) {
//        JSONObject itemInfo =  loadJSONFromFile();
//        JSONArray items = itemInfo.getJSONArray("items");
//
//        for (int i = 0; i < items.length(); i++) {
//            JSONObject item = items.getJSONObject(i);
//            if (item.getInt("id") == itemId) {
//                    item.put("quantity", newQuantity);
//                    System.out.println("Updated item: " + item);
//                break;
//            }
//        }
//    }
    
    // Method to write JSON data to file
    private static void writeJSONToFile() {
        JSONObject itemInfo =  loadJSONFromFile();

        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(itemInfo.toString(4)); // Indent with 4 spaces for readability
            System.out.println("Successfully updated JSON file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * @param items
     * @param itemID
     * @param amount
     * @return
     */
    public static Map<String, Integer> addToBag(Map<String, Integer> items, String itemID, int amount) {
        JSONObject itemInfo =  loadJSONFromFile();
        JSONObject item = itemInfo.getJSONObject(itemID);
        if(item == null) { return items; }
        int quantity = itemInfo.getInt("quantity");
        int temp = 0;

        while(quantity > 0 & temp < amount) {
            if(items.containsKey(itemID)) {
                temp++;
                quantity--;
                items.put(itemID, items.get(itemID) + 1);
            }
        }
        itemInfo.put("quantity", items.get(quantity));
        return items;
    }

    /**
     *
     * @param items
     * @param paymentAmount
     * @return
     */
    public static double completeSale(Map<String, Integer> items, int paymentAmount){
        JSONObject itemInfo =  loadJSONFromFile();
        double total = 0;

        for(String id: items.keySet()){
            JSONObject item = itemInfo.getJSONObject(id);
            total += ((double)item.get("price")) * items.get(id);
            if(total > paymentAmount) {
                for(String itemID: items.keySet()) {
                    stockItem(itemID, items.get(itemID));
                }
                return -1;
            }
        }

        return total;
    }

//    public static int orderItems(String[] itemIDs){
//        JSONObject itemInfo =  loadJSONFromFile();
//        stockItem(4, 10);
//        return 0;
//    }

    public static void orderNewItem(){}

    public static void stockItem(String itemID, int orderAmount){
        
    }
}
