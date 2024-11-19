package GasChainPackage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public interface FileUtility {
    boolean writeToFile(String fileName, JSONObject jsonObject) throws IOException;

    static boolean updateItemInJSON(int itemId, String keyToUpdate, Object newValue, String fileName) {
        JSONObject allItems = loadJSONFromFile(fileName);
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
        writeJSONToFile(allItems, fileName);
        return true;
    }
    static void writeJSONToFile(JSONObject itemInfo, String filename) {
        if (itemInfo == null) {
            System.out.println("Error: No data to write to file.");
            return;
        }
        // Write JSON data to file
        try (FileWriter file = new FileWriter(filename)) {
            file.write(itemInfo.toString(4)); // Indent with 4 spaces for readability
        } catch (Exception e) {
            System.out.println("Error: Unable to write to file at " + filename);
            e.printStackTrace();
        }
    }

    static JSONObject loadJSONFromFile(String fileName) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            return( new JSONObject(content));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}