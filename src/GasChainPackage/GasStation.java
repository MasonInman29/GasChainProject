package GasChainPackage;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GasStation {
    private double balance;
    private final Bank bank;
    private static final String FILE_PATH = "../itemInformation.json";

    public GasStation(Bank bank) {
        this.balance = 0.0;
        this.bank = bank;
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

    // Method to update the quantity of an item by ID
    private static void updateItemQuantity(int itemId, int newQuantity) {
        JSONObject itemInfo =  loadJSONFromFile();
        JSONArray items = itemInfo.getJSONArray("items");
    
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            if (item.getInt("id") == itemId) {
                    item.put("quantity", newQuantity);
                    System.out.println("Updated item: " + item);
                break;
            }
        }
    }
    
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
        bank.deposit(paymentInfo, amount);
        balance += amount;
        System.out.println("Station balance updated: $" + balance);
    }

    public double getBalance() {
        return balance;
    }

    //-------------------STORE------------------//

    /**
     * 
     * @param itemIDs of each item in the sale by its ID
     * @return price as a double
     */
    public static double sale(String[] itemIDs){
        JSONObject itemInfo =  loadJSONFromFile();

        double total = 0;
        for(String id: itemIDs){
            JSONObject item = itemInfo.getJSONObject(id);


        }

        return 0;
    }

    public static int orderItems(String[] itemIDs){

        stockItem(4, 10);
        return 0;
    }

    public static void orderNewItem(){}

    public static int stockItem(int itemID, int orderAmount){
        
        return 0;
    }
}