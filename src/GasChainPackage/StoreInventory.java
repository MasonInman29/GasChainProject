package GasChainPackage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreInventory {
    private static final String FILE_PATH = "itemInformation.json";
    private int reorderThreshold;

    public StoreInventory() {
        this.reorderThreshold = 10;
    }

    public Map<String, Integer> getStockLevels() {
        Map<String, Integer> stockLevel = new HashMap<>();
        JSONArray items = FileUtility.loadJSONFromFile(FILE_PATH);
        
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            stockLevel.put(item.getString("name"), item.getInt("quantity"));
        }
        return stockLevel;
    }

    public boolean checkStockItem(String itemName) {
        JSONArray items = FileUtility.loadJSONFromFile(FILE_PATH);
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            if (item.getString("name").toLowerCase().equals(itemName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public String getActualItemName(String searchName) {
        JSONArray items = FileUtility.loadJSONFromFile(FILE_PATH);
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            if (item.getString("name").toLowerCase().equals(searchName.toLowerCase())) {
                return item.getString("name");
            }
        }
        return null;
    }

    public boolean updateStock(List<String> items, List<Integer> quantities) {
        try {
            JSONArray allItems = FileUtility.loadJSONFromFile(FILE_PATH);
            
            // Create case-insensitive map for item lookup
            Map<String, JSONObject> itemMap = new HashMap<>();
            for (int i = 0; i < allItems.length(); i++) {
                JSONObject item = allItems.getJSONObject(i);
                itemMap.put(item.getString("name").toLowerCase(), item);
            }
    
            // Update quantities for each item
            for (int i = 0; i < items.size(); i++) {
                String searchName = items.get(i).toLowerCase();
                int quantityChange = quantities.get(i);
                
                // Find the item in our map
                JSONObject item = itemMap.get(searchName);
                
                if (item == null) {
                    System.out.println("Error: Item not found: " + items.get(i));
                    return false;
                }
    
                // Get current quantities and limits
                int currentQuantity = item.getInt("quantity");
                int maxCapacity = item.getInt("maxCapacity");
                int newQuantity = currentQuantity + quantityChange;
                
                // Validate new quantity
                if (newQuantity < 0) {
                    System.out.println("Error: Cannot reduce quantity below 0 for " + item.getString("name"));
                    System.out.println("Current quantity: " + currentQuantity);
                    System.out.println("Attempted change: " + quantityChange);
                    return false;
                }
                
                if (newQuantity > maxCapacity) {
                    System.out.println("Error: Cannot exceed maximum capacity for " + item.getString("name"));
                    System.out.println("Maximum capacity: " + maxCapacity);
                    System.out.println("Attempted new quantity: " + newQuantity);
                    return false;
                }
                
                // Update the quantity in the JSONObject
                item.put("quantity", newQuantity);
            }
            
            // Write the updated inventory back to file
            FileUtility.writeJSONToFile(allItems, FILE_PATH);
            return true;
            
        } catch (Exception e) {
            System.out.println("Error updating inventory: " + e.getMessage());
            return false;
        }
    }

    public void reorder(String itemName) {
        JSONArray items = FileUtility.loadJSONFromFile(FILE_PATH);
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            if (item.getString("name").equals(itemName)) {
                int currentQuantity = item.getInt("quantity");
                if (currentQuantity <= reorderThreshold) {
                    System.out.println("REORDER NOTICE: " + itemName);
                    System.out.println("Current quantity: " + currentQuantity);
                    System.out.println("Reorder threshold: " + reorderThreshold);
                    System.out.println("Max capacity: " + item.getInt("maxCapacity"));
                }
                break;
            }
        }
    }
    
    public int getReorderThreshold() {
        return reorderThreshold;
    }
}
