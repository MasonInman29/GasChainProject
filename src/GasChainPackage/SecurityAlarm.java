package GasChainPackage;

import java.util.ArrayList;
import java.util.List;

public class SecurityAlarm {
    private boolean isArmed;
    private List<String> log;

    public SecurityAlarm() {
        this.isArmed = false;
        this.log = new ArrayList<>();
    }

    // Activate the alarm
    public void activateAlarm(String employeeName) {
        if (!isArmed) {
            isArmed = true;
            System.out.println("Alarm has been activated. Premises are secured.");
            logEvent("Activated by " + employeeName);
        } else {
            System.out.println("Alarm is already armed.");
        }
    }

    // Deactivate the alarm
    public void deactivateAlarm(String employeeName) {
        if (isArmed) {
            isArmed = false;
            System.out.println("Alarm has been deactivated. Premises are open for business.");
            logEvent("Deactivated by " + employeeName);
        } else {
            System.out.println("Alarm is already disarmed.");
        }
    }

    // Trigger the alarm in response to a security threat
    public void triggerAlarm(String reason) {
        System.out.println("Alarm triggered! Reason: " + reason);
        logEvent("Triggered due to: " + reason);
        notifySecurity();
    }

    // Log events
    private void logEvent(String details) {
        String entry = "Timestamp: " + System.currentTimeMillis() + " - " + details;
        log.add(entry);
    }

    // Display the alarm log
    public void viewLog() {
        System.out.println("\n--- Security Alarm Log ---");
        if (log.isEmpty()) {
            System.out.println("No alarm events logged.");
        } else {
            for (String entry : log) {
                System.out.println(entry);
            }
        }
    }

    // Notify security personnel or law enforcement
    private void notifySecurity() {
        System.out.println("Security personnel and law enforcement have been notified.");
    }

    // Check if the alarm is armed
    public boolean isArmed() {
        return isArmed;
    }
}
