package com.restaurant.restaurantapp;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RestaurantModel {

    private static final String LOG_FILE = "transaction_log.txt";

    public void saveTransaction(double totalAmount, double cashPaid, double change) {
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            FileWriter writer = new FileWriter(LOG_FILE, true);
            String transaction = String.format(
                    "Time: %s | Total: %.2f | Cash: %.2f | Change: %.2f%n",
                    now.format(format), totalAmount, cashPaid, change
            );
            writer.write(transaction);
            writer.close();

            System.out.println("Transaction saved to " + LOG_FILE);
        } catch (IOException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
            e.printStackTrace();
        }
    }
}