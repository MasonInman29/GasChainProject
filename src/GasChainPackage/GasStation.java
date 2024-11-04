package GasChainPackage;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GasStation {
    private int stationID;
    private StoreInventory storeInventory;
    private double balance;
    private String location;
    private Employee cashier;
    private List<String> fuelTypesOffered;
    private List<String> convenienceItems;
    private List<String> servicesOffered;
    private Bank bank;
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

    public void deposit(String paymentInfo, double amount) {
        if (bank.deposit(paymentInfo, amount)) {
            balance += amount;
            recordTransaction("deposit", amount);
            System.out.println("Station balance updated: $" + balance);
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

    //-------------------STORE------------------//

    public static int sellItem(int list) {
        return 0;
    }

    public static int orderItems() {
        return 0;
    }
}
