package GasChainPackage;

class MaintenanceTechnician {
    private String name;
    private String expertise; // e.g., "Electrical", "Mechanical", etc.

    public MaintenanceTechnician(String name, String expertise) {
        this.name = name;
        this.expertise = expertise;
    }

    public void inspectIssue(MaintenanceRequest request) {
        System.out.println("Technician " + name + " (Expertise: " + expertise + "): Inspecting issue...");
        System.out.println("Issue Description: " + request.getIssueDescription());
        System.out.println("Location: " + request.getLocation());
        System.out.println("Priority Level: " + request.getPriorityLevel());
        System.out.println("Inspection completed. Preparing for repair.");
    }

    public void performMaintenance(MaintenanceRequest request) {
        System.out.println("Technician " + name + ": Performing maintenance for issue: " + request.getIssueDescription());
        System.out.println("Repairing the issue at " + request.getLocation() + "...");

        // Simulate repair process
        try {
            Thread.sleep(2000); // Simulating time taken for repair
        } catch (InterruptedException e) {
            System.out.println("Maintenance process interrupted!");
        }

        System.out.println("Maintenance completed for: " + request.getIssueDescription());
        request.setStatus("Completed");
    }

    public void reportStatus(MaintenanceRequest request) {
        System.out.println("Technician " + name + ": Reporting status...");
        System.out.println("Issue: " + request.getIssueDescription());
        System.out.println("Location: " + request.getLocation());
        System.out.println("Status: " + request.getStatus());
        System.out.println("Maintenance task is successfully resolved.");
    }
}

