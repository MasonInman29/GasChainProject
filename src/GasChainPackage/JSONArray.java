package GasChainPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Bare-Bones Implementation of JSONArray Class
 * This Class mimics the org.json.JSONArray Class
 */

public class JSONArray {
    private List<Object> list;

    public JSONArray() {
        list = new ArrayList<>();
    }

    public void put(Object value) {
        list.add(value);
    }

    public Object get(int index) {
        return list.get(index);
    }

    public JSONObject getJSONObject(int index) {
        Object obj = list.get(index);
        return obj instanceof JSONObject ? (JSONObject) obj : null;
    }

    public JSONObject getJSONObject(String key) {
        for (Object obj : list) {
            if (obj instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) obj;
                if (jsonObject.keySet().contains(key)) {
                    return jsonObject;
                }
            }
        }
        return null;
    }

    public JSONObject searchByID(int id) {
        // Search for the item with the given ID
        JSONObject targetItem = null;
        for (int i = 0; i < list.size(); i++) {
            JSONObject item = getJSONObject(i);
            if (item.getInt("id") == id) {
                targetItem = item;
                break;
            }
        }
        return targetItem;
    }

    // Add method to remove an element at a given index
    public boolean remove(int index) {
        if (index < 0 || index >= list.size()) {
            System.out.println("Error: Index out of bounds.");
            return false;
        }
        try {
            list.remove(index);
            return true;
        } catch (Exception e) {
            System.out.println("Error: Unable to remove item at index " + index + ".");
            e.printStackTrace();
            return false;
        }
    }

    // Add helper method to remove an object matching a key-value pair
    public boolean remove(String key, Object value) {
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject = (JSONObject) list.get(i);
            if (jsonObject.has(key) && jsonObject.get(key).equals(value)) {
                list.remove(i);
                return true;
            }
        }
        System.out.println("Error: No matching object found to remove.");
        return false;
    }

    // Additional methods for JSONArray (e.g., adding, retrieving elements)
    public void put(JSONObject jsonObject) {
        list.add(jsonObject);
    }

    public int length() {
        return list.size();
    }

    public String toString() {
        return list.toString();
    }
}

