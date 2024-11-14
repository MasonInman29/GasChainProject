package GasChainPackage;

//import org.json.JSONException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bare-Bones Implementation of JSONObject Class
 * This Class mimics the org.json.JSONObject Class
 */
public class JSONObject {
    private Map<String, Object> data;

    public JSONObject() {
        data = new HashMap<>();
    }

    public JSONObject(String jsonString) {
        data = parse(jsonString);
    }
    public void put(String key, Object value) {
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }

    public JSONObject getJSONObject(String key) {
        Object obj = data.get(key);
        return obj instanceof JSONObject ? (JSONObject) obj : null;
    }

    public int getInt(String key) {
        Object obj = data.get(key);
        return obj instanceof Integer ? (int) obj : 0;
    }

    public double getDouble(String key) {
        Object obj = data.get(key);
        return obj instanceof Double ? (double) obj : 0.0;
    }

    public String getString(String key) {
        Object obj = data.get(key);
        return obj instanceof String ? (String) obj : null;
    }

    public Set<String> keySet() {
        return data.keySet();
    }

    public JSONObject optJSONObject(String key) {
        Object obj = data.get(key);
        return obj instanceof JSONObject ? (JSONObject) obj : null;
    }

    private Map<String, Object> parse(String jsonString) {
        Map<String, Object> parsedData = new HashMap<>();
        jsonString = jsonString.trim();

        if (jsonString.startsWith("{") && jsonString.endsWith("}")) {
            jsonString = jsonString.substring(1, jsonString.length() - 1).trim();

            String[] entries = jsonString.split(",");
            for (String entry : entries) {
                String[] keyValue = entry.split(":");
                String key = keyValue[0].trim().replaceAll("^\"|\"$", ""); // Remove surrounding quotes
                String value = keyValue[1].trim();

                if (value.startsWith("\"")) {
                    // It's a string
                    value = value.replaceAll("^\"|\"$", ""); // Remove surrounding quotes
                    parsedData.put(key, value);
                } else if (value.equals("true") || value.equals("false")) {
                    // It's a boolean
                    parsedData.put(key, Boolean.parseBoolean(value));
                } else if (value.contains(".")) {
                    // It's a double
                    parsedData.put(key, Double.parseDouble(value));
                } else {
                    // It's an integer
                    parsedData.put(key, Integer.parseInt(value));
                }
            }
        }
        return parsedData;
    }
    public String toString() {
        return data.toString();
    }
    public String toString(int indentFactor) throws Exception {
        StringWriter w = new StringWriter();
        synchronized(w.getBuffer()) {
            return this.write(w, indentFactor, 0).toString();
        }
    }

    public StringWriter write(StringWriter writer, int indentFactor, int indent) throws Exception {
        writer.write("{\n");
        boolean first = true;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (!first) {
                writer.write(",\n");
            }
            // Indentation for each key-value pair
            for (int i = 0; i < indent + indentFactor; i++) {
                writer.write(" ");
            }

            writer.write("\"" + entry.getKey() + "\": ");

            // Write value based on its type
            Object value = entry.getValue();
            if (value instanceof JSONObject) {
                ((JSONObject) value).write(writer, indentFactor, indent + indentFactor);
            } else if (value instanceof String) {
                writer.write("\"" + value + "\"");
            } else {
                writer.write(value.toString());
            }

            first = false;
        }

        writer.write("\n");
        for (int i = 0; i < indent; i++) {
            writer.write(" ");
        }
        writer.write("}");
        return writer;
    }
}
