package GasChainPackage;

import java.io.FileWriter;
import java.io.IOException;

public class Bank implements FileUtility {
    private int bankID;
    private double moneyInAccount;
    private boolean isOpen;

    public Bank(int bankID, double initialBalance) {
        this.bankID = bankID;
        this.moneyInAccount = initialBalance;
        this.isOpen = true;
    }

    public boolean deposit(String paymentInfo, double amount) {
        if (!isOpen) {
            System.out.println("Bank is currently closed. Please try again later.");
            return false;
        }

        JSONObject depositJSON = new JSONObject();
        depositJSON.put("transaction", paymentInfo);
        depositJSON.put("amount", amount);
        depositJSON.put("bankId", this.bankID);
        depositJSON.put("timestamp", System.currentTimeMillis());
        depositJSON.put("status", "deposited");

        try {
            if (writeToFile("bankDeposits.json", depositJSON)) {
                moneyInAccount += amount;
                System.out.println("Deposit recorded successfully.");
                return true;
            } else {
                System.out.println("Failed to record deposit.");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Error recording deposit: " + e.getMessage());
            return false;
        }
    }

    public boolean withdraw() {
        try {
            double withdrawalAmount = this.moneyInAccount;
            this.moneyInAccount = 0;

            // Create JSON record of the withdrawal
            JSONObject withdrawJSON = new JSONObject();
            withdrawJSON.put("bankID", this.bankID);
            withdrawJSON.put("amount", withdrawalAmount);
            withdrawJSON.put("balance", this.moneyInAccount);
            withdrawJSON.put("timestamp", System.currentTimeMillis());

            return writeToFile("bankTransactions.json", withdrawJSON);
        } catch (IOException e) {
            System.out.println("Error processing withdrawal: " + e.getMessage());
            return false;
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
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

