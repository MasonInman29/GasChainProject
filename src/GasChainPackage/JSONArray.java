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

    public int length() {
        return list.size();
    }

    public String toString() {
        return list.toString();
    }
}

