package GasChainPackage;

import org.json.JSONObject;
import java.util.List;
import java.util.Map;

public class Employee {
    private int employeeID;
    private String name;
    private String role;
    private GasStation station;

    public Employee(int employeeID, String name, String role) {
        this.employeeID = employeeID;
        this.name = name;
        this.role = role;
    }

    public void setStation(GasStation station) {
        this.station = station;
    }

    public void stockItems() {
        if (station == null) {
            System.out.println("Error: Employee not assigned to a station");
            return;
        }

        Map<String, Integer> currentInventory = station.getAllInventory();
        System.out.println("Current Inventory Levels:");
        currentInventory.forEach((item, quantity) ->
                System.out.println(item + ": " + quantity));
    }

    public boolean updateInventory(List<String> items, List<Integer> quantities) {
        if (station == null) {
            System.out.println("Error: Employee not assigned to a station");
            return false;
        }

        if (items.size() != quantities.size()) {
            System.out.println("Error: Mismatch between items and quantities");
            return false;
        }

        boolean updateSuccess = station.updateInventoryLevels(items, quantities);
        if (!updateSuccess) {
            System.out.println("Invalid inventory count submitted. Please recount and try again.");
            return false;
        }

        return true;
    }
}
