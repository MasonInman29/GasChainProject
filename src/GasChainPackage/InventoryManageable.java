package GasChainPackage;

public interface InventoryManageable {
    double checkFuelLevel(String fuelType);
    void updateInventory(String fuelType, double quantity);
}
