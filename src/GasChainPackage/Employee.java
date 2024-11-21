package GasChainPackage;

import java.util.List;
import java.util.Map;

public class Employee {
    private int employeeID;
    private String name;
    private String role;
    private GasStation station;

    public int getEmployeeID() {
        return employeeID;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

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

    void prepareForDelivery() {
        System.out.println(name + " is preparing for fuel delivery...");
        System.out.println("Checking storage tanks and ensuring they are ready for new fuel.");
    }

    public MaintenanceRequest logMaintenanceRequest(String description, String location, String priorityLevel) {
        System.out.println("Employee: Logging maintenance request.");
        return new MaintenanceRequest(description, location, priorityLevel);
    }

    public void performDailyInspection(FireExtinguisher extinguisher) {
        System.out.println("Employee: Conducting daily inspection of extinguisher at " + extinguisher.getLocation());
        if (extinguisher.isFunctional()) {
            System.out.println("The extinguisher is functional.");
        } else {
            System.out.println("The extinguisher is not functional. Report to Station Manager.");
        }
    }

    public void respondToEmergency(FireExtinguisher extinguisher, String fireType) {
        System.out.println("Employee: Responding to fire emergency of type: " + fireType);
        System.out.println("Using extinguisher at " + extinguisher.getLocation());
        if (extinguisher.isFunctional()) {
            System.out.println("Fire successfully controlled.");
        } else {
            System.out.println("Fire extinguisher is malfunctioning. Evacuating and calling emergency services.");
        }
    }
}
