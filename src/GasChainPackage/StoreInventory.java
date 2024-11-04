package GasChainPackage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreInventory {
    private Map<String, Integer> stockLevel;
    private int reorderThreshold;
    private List<String> items;

    public StoreInventory() {
        this.stockLevel = new HashMap<>();
        this.reorderThreshold = 10;
    }

    public Map<String, Integer> getStockLevels() {
        return new HashMap<>(stockLevel);
    }

    public boolean checkStockItem(String item) {
        return stockLevel.containsKey(item);
    }

    public boolean updateStock(List<String> items, List<Integer> quantities) {
        try {
            for (int i = 0; i < items.size(); i++) {
                String item = items.get(i);
                int quantityChange = quantities.get(i);

                if (stockLevel.containsKey(item)) {
                    int currentQuantity = stockLevel.get(item);

                    int newQuantity = currentQuantity + quantityChange;

                    if (newQuantity <= 0) {
                        // Remove item if quantity becomes 0 or negative
                        stockLevel.remove(item);
                    } else {
                        stockLevel.put(item, newQuantity);
                    }
                } else {
                    // If item doesn't exist and quantity is positive, add it
                    if (quantityChange > 0) {
                        stockLevel.put(item, quantityChange);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error updating inventory: " + e.getMessage());
            return false;
        }
    }

    public void reorder(String item) {
        System.out.println("Reorder notice created for: " + item);
    }

    public int getReorderThreshold() {
        return reorderThreshold;
    }
}
