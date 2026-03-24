package com.restaurant.restaurantapp;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RestaurantModel {

    private static final String LOG_FILE = "transaction_log.txt";

    public void saveTransaction(double totalAmount, double cashPaid, double change,
                                String transactionTime, List<String> items) {
        try {
            LocalDateTime now = LocalDateTime.now();
            String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            FileWriter writer = new FileWriter(LOG_FILE, true);
            writer.write("=======================================================\n");
            writer.write("       LA FAMIGLIA DI RAVEN - TRANSACTION RECEIPT\n");
            writer.write("=======================================================\n");
            writer.write("Date            : " + date + "\n");
            writer.write("Time            : " + transactionTime + "\n");
            writer.write("-------------------------------------------------------\n");
            writer.write("ITEMS ORDERED:\n");
            for (String item : items) {
                writer.write("  - " + item + "\n");
            }
            writer.write("-------------------------------------------------------\n");
            writer.write(String.format("Total Amount    : M%.2f%n", totalAmount));
            writer.write(String.format("Cash Tendered   : M%.2f%n", cashPaid));
            writer.write(String.format("Change Given    : M%.2f%n", change));
            writer.write("=======================================================\n");
            writer.write("    Thank you! Enjoy your meal! Kea leboha!\n");
            writer.write("=======================================================\n\n");
            writer.close();

            System.out.println("Transaction saved to " + LOG_FILE);
        } catch (IOException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
            e.printStackTrace();
        }
    }
}