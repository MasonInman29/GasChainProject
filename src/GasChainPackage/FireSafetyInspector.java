package GasChainPackage;

import java.util.List;

public class FireSafetyInspector {
    public void conductInspection(FireExtinguisher extinguisher) {
        System.out.println("Fire Safety Inspector: Conducting official inspection of extinguisher at " + extinguisher.getLocation());
        if (!extinguisher.isFunctional()) {
            System.out.println("Inspector: Non-compliant. Needs repair or replacement.");
        } else {
            System.out.println("Inspector: Compliant. Fire extinguisher is in operational condition.");
        }
    }

    // Conducts training for employees
    public void trainEmployees(List<Employee> employees) {
        System.out.println("\nFire Safety Inspector: Conducting fire safety training for employees...");
        for (Employee employee : employees) {
            System.out.println("Training employee: " + employee.getName());
            System.out.println("- Explaining fire hazards.");
            System.out.println("- Demonstrating PASS technique: Pull, Aim, Squeeze, Sweep.");
        }
        System.out.println("Training completed for all employees.");
    }

    // Conducts training for station managers
    public void trainStationManagers(List<StationManager> managers) {
        System.out.println("\nFire Safety Inspector: Conducting fire safety training for station managers...");
        for (StationManager manager : managers) {
            System.out.println("Training station manager: " + manager.getName());
            System.out.println("- Reviewing fire safety compliance protocols.");
            System.out.println("- Discussing fire extinguisher maintenance schedules.");
            System.out.println("- Conducting mock fire safety audits.");
        }
        System.out.println("Training completed for all station managers.");
    }

    // Conducts a fire drill
    public void conductFireDrill() {
        System.out.println("\nFire Safety Inspector: Conducting a fire drill at the station...");
        System.out.println("- Simulating a fire scenario.");
        System.out.println("- Testing evacuation procedures.");
        System.out.println("- Evaluating employee response times and extinguisher usage.");
        System.out.println("Fire drill completed. Feedback provided to the station.");
    }
}
