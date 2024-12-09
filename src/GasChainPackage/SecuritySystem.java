package GasChainPackage;

import java.util.ArrayList;
import java.util.List;

public class SecuritySystem {
    private List<SecurityCamera> cameras;

    public SecuritySystem() {
        cameras = new ArrayList<>();
        // Adding some default cameras
        cameras.add(new SecurityCamera(1, "Fuel Pump Area"));
        cameras.add(new SecurityCamera(2, "Store Entrance"));
        cameras.add(new SecurityCamera(3, "Parking Lot"));
    }

    public List<SecurityCamera> getCameras() {
        return cameras;
    }

    public void monitorCameras() {
        System.out.println("\n--- Monitoring Security Cameras ---");
        for (SecurityCamera camera : cameras) {
            camera.displayFeed();
        }
    }
}
