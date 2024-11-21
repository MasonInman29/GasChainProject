package GasChainPackage;

import java.util.*;

class MaintenanceRequest {
    private String issueDescription;
    private String location;
    private String priorityLevel;
    private String status;

    public MaintenanceRequest(String issueDescription, String location, String priorityLevel) {
        this.issueDescription = issueDescription;
        this.location = location;
        this.priorityLevel = priorityLevel;
        this.status = "Open";
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public String getLocation() {
        return location;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Request [Description: " + issueDescription + ", Location: " + location + ", Priority: " + priorityLevel + ", Status: " + status + "]";
    }
}
