package GasChainPackage;


public class SecurityCamera {
    private int cameraId;
    private String location;
    private boolean isActive;

    public SecurityCamera(int cameraId, String location) {
        this.cameraId = cameraId;
        this.location = location;
        this.isActive = true;
    }

    public void displayFeed() {
        if (isActive) {
            System.out.println("Displaying live feed from Camera " + cameraId + " at location: " + location);
        } else {
            System.out.println("Camera " + cameraId + " at location: " + location + " is offline.");
        }
    }

    public int getCameraId() {
        return cameraId;
    }

    public String getLocation() {
        return location;
    }

    public boolean isActive() {
        return isActive;
    }
}

