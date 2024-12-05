package GasChainPackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public interface FileUtility {
    boolean writeToFile(String fileName, JSONObject jsonObject) throws IOException;

    static boolean updateItemInJSON(int itemId, String keyToUpdate, Object newValue, String fileName) {
        JSONArray allItems = loadJSONFromFile(fileName);
        if (allItems == null) {
            System.out.println("Error: Could not load JSON data from " + fileName + ".");
            return false;
        }

        JSONObject item = allItems.searchByID(itemId);
        if (item == null)  {
            return false;
        }

        // Update the specified key with the new value
        item.put(keyToUpdate, newValue);

        // Save the updated JSON back to the file
        writeJSONToFile(allItems, fileName);
        return true;
    }

    // Write the JSONArray to a file
    static void writeJSONToFile(JSONArray itemInfo, String filename) {
        if (itemInfo == null) {
            System.out.println("Error: No data to write to file.");
            return;
        }

        try (FileWriter file = new FileWriter(filename)) {
            file.write("[");  // Start of the JSON array

            for (int i = 0; i < itemInfo.length(); i++) {
                JSONObject item = itemInfo.getJSONObject(i);
                file.write(item.toString()); // Write each JSONObject as a string

                if (i < itemInfo.length() - 1) {
                    file.write(",\n");  // Add a comma and newline between items, except after the last item
                }
            }

            file.write("]");  // End of the JSON array
        } catch (IOException e) {
            System.out.println("Error: Unable to write to file at " + filename);
            e.printStackTrace();
        }
    }

    // Load JSON data from a file
    static JSONArray loadJSONFromFile(String fileName) {
        JSONArray jsonArray = new JSONArray();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;

            // Read the entire file content
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            // Remove potential surrounding square brackets if present
            String jsonString = jsonContent.toString().trim();
            if (jsonString.startsWith("[") && jsonString.endsWith("]")) {
                jsonString = jsonString.substring(1, jsonString.length() - 1).trim(); // Remove brackets
//                String[] items = jsonString.split("},");

                // Remove leading and trailing spaces, and split by "}, {"
                String[] items = jsonString.trim().split("},\\s*\\{");


                // Add curly braces back to each part and store them in the result array
                for (int i = 0; i < items.length && items.length != 1; i++) {
                    if (i == 0) {
                        items[i] = items[i] + "}";
                    } else if (i == items.length - 1) {
                        items[i] = "{" + items[i];
                    } else {
                        items[i] = "{" + items[i] + "}";
                    }

                }
                // Parse each item (handle edge cases like object separation)
                for (String itemString : items) {
                    if (!itemString.startsWith("{")) itemString = "{" + itemString;
                    if (!itemString.endsWith("}")) itemString = itemString + "}";
                    jsonArray.put(new JSONObject(itemString));  // Add the JSONObject to the JSONArray
                }
            } else {
                System.out.println("Error: The file does not contain a valid JSON array.");
            }

        } catch (IOException e) {
            System.out.println("Error: Unable to read file at " + fileName);
            e.printStackTrace();
        }
        return jsonArray;
    }
    static void addItemToJSON(JSONObject newItem, String fileName) {
        // Load existing data from the file
        JSONArray allItems = loadJSONFromFile(fileName);
        if (allItems == null) {
            System.out.println("Error: Could not load JSON data from " + fileName + ".");
            return;
        }

        // Add the new item to the JSONArray
        allItems.put(newItem);

        // Write the updated JSONArray back to the file
        writeJSONToFile(allItems, fileName);
    }

    static void removeItemFromJSON(int itemId, String fileName) {
        // Load existing data from the file
        JSONArray allItems = loadJSONFromFile(fileName);
        if (allItems == null) {
            System.out.println("Error: Could not load JSON data from " + fileName + ".");
            return;
        }

        // Find and remove the item with the specified ID
        for (int i = 0; i < allItems.length(); i++) {
            JSONObject item = allItems.getJSONObject(i);
            if (item.getInt("id") == itemId) { // Assuming "id" is the key for itemId
                allItems.remove(i);

                // Write the updated JSONArray back to the file
                writeJSONToFile(allItems, fileName);
            }
        }

        // If the item wasn't found
        System.out.println("Error: Item with ID " + itemId + " not found in " + fileName + ".");
        return;
    }

}
