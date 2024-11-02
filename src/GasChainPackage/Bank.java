package GasChainPackage;

import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class Bank implements FileUtility {

    public void deposit(String paymentInfo, double amount) {
        // GasChainPackage.Bank logic for deposit
        JSONObject depositJSON = new JSONObject();
        depositJSON.put("transaction", paymentInfo);
        depositJSON.put("amount", amount);
        depositJSON.put("status", "deposited");

        try {
            if (writeToFile("bankDeposits.json", depositJSON)) {
                System.out.println("Deposit recorded successfully.");
            } else {
                System.out.println("Failed to record deposit.");
            }
        } catch (IOException e) {
            System.out.println("Error recording deposit: " + e.getMessage());
        }
    }

    @Override
    public boolean writeToFile(String fileName, JSONObject jsonObject) throws IOException {
        try (FileWriter file = new FileWriter(fileName, true)) { // Append to file
            file.write(jsonObject.toString() + System.lineSeparator());
            return true;
        } catch (IOException e) {
            System.out.println("Failed to write to file: " + e.getMessage());
            return false;
        }
    }
}

