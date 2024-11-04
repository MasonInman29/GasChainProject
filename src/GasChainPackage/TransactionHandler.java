package GasChainPackage;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class TransactionHandler implements FileUtility {

    public boolean processPayment(String paymentInfo, GasStation station, double amount) {
        JSONObject paymentJSON = new JSONObject();
        paymentJSON.put("paymentInfo", paymentInfo);
        paymentJSON.put("status", "success");

        try {
            if (writeToFile("transactionLog.json", paymentJSON)) {
                System.out.println("Payment processed successfully.");
                station.deposit(paymentInfo, amount);
                return true;
            } else {
                System.out.println("Failed to process payment.");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Error writing to transaction log: " + e.getMessage());
            return false;
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

