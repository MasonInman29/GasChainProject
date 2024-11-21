package GasChainPackage;

public class FireExtinguisher {
    private String location;
    private boolean isFunctional;
    private String expirationDate;

    public FireExtinguisher(String location, boolean isFunctional, String expirationDate) {
        this.location = location;
        this.isFunctional = isFunctional;
        this.expirationDate = expirationDate;
    }

    public String getLocation() {
        return location;
    }

    public boolean isFunctional() {
        return isFunctional;
    }

    public void setFunctional(boolean functional) {
        isFunctional = functional;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String toString() {
        return "Fire Extinguisher at " + location + " [Functional: " + isFunctional + ", Expiration: " + expirationDate + "]";
    }
}
