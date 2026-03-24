package com.restaurant.restaurantapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RestaurantController {

    // Basotho Menu - Dinner
    @FXML private CheckBox cb1, cb2, cb3;
    // Italian Menu - Dinner
    @FXML private CheckBox cb4, cb5, cb6;

    // Basotho Menu - Dessert
    @FXML private RadioButton rb1, rb2, rb3;
    // Italian Menu - Dessert
    @FXML private RadioButton rb4, rb5, rb6;

    // Drinks ComboBoxes
    @FXML private ComboBox<String> drinksCombo1;
    @FXML private ComboBox<String> drinksCombo2;

    // Order Summary
    @FXML private TextField cashPaidField;
    @FXML private Label totalLabel;
    @FXML private Label changeLabel;
    @FXML private Label transactionTimeLabel;

    // Selected Items List
    @FXML private ListView<String> selectedItemsList;

    // Action Buttons
    @FXML private Button totalBtn, changeBtn, resetBtn, exitBtn;

    // Tab Pane
    @FXML private TabPane menuTabs;

    private RestaurantModel model;
    private double totalAmount = 0;
    private ObservableList<String> selectedItems;

    // Menu item prices (in cents)
    private static final double PAPA_LE_MOROHO = 4500;
    private static final double LIJO_TSA_SETHO = 5500;
    private static final double NAMA_EA_KHOMO = 7500;
    private static final double PASTA_CARBONARA = 8500;
    private static final double PIZZA_MARGHERITA = 9500;
    private static final double RISOTTO = 11000;

    private static final double MAKOENYA = 2500;
    private static final double LIKHOBE = 3000;
    private static final double MOTOHO = 2000;
    private static final double TIRAMISU = 4500;
    private static final double PANNA_COTTA = 4000;
    private static final double GELATO = 3500;

    @FXML
    public void initialize() {
        System.out.println("Restaurant Controller Started");
        model = new RestaurantModel();
        selectedItems = FXCollections.observableArrayList();
        selectedItemsList.setItems(selectedItems);

        // Initialize Basotho drinks
        drinksCombo1.setItems(FXCollections.observableArrayList(
                "Joala ba Sesotho - M15.00",
                "Motoho oa Poone - M10.00",
                "Water - M5.00",
                "Soft Drink - M12.00"
        ));

        // Initialize Italian drinks
        drinksCombo2.setItems(FXCollections.observableArrayList(
                "House Wine - M80.00",
                "Premium Wine - M120.00",
                "Espresso - M25.00",
                "Cappuccino - M35.00",
                "Sparkling Water - M15.00"
        ));

        // Setup toggle groups for desserts
        ToggleGroup basothoDessertGroup = new ToggleGroup();
        rb1.setToggleGroup(basothoDessertGroup);
        rb2.setToggleGroup(basothoDessertGroup);
        rb3.setToggleGroup(basothoDessertGroup);

        ToggleGroup italianDessertGroup = new ToggleGroup();
        rb4.setToggleGroup(italianDessertGroup);
        rb5.setToggleGroup(italianDessertGroup);
        rb6.setToggleGroup(italianDessertGroup);

        // Add listeners for all menu items
        setupMenuListeners();

        // Button actions
        totalBtn.setOnAction(e -> {
            updateTotal();
            showAlert("Total Amount", "Your total is: M" + String.format("%.2f", totalAmount / 100));
        });

        changeBtn.setOnAction(e -> calculateChange());
        resetBtn.setOnAction(e -> resetOrder());
        exitBtn.setOnAction(e -> confirmExit());
    }

    private void setupMenuListeners() {
        // Basotho Dinner
        cb1.setOnAction(e -> updateSelectedItems());
        cb2.setOnAction(e -> updateSelectedItems());
        cb3.setOnAction(e -> updateSelectedItems());

        // Italian Dinner
        cb4.setOnAction(e -> updateSelectedItems());
        cb5.setOnAction(e -> updateSelectedItems());
        cb6.setOnAction(e -> updateSelectedItems());

        // Basotho Dessert
        rb1.setOnAction(e -> updateSelectedItems());
        rb2.setOnAction(e -> updateSelectedItems());
        rb3.setOnAction(e -> updateSelectedItems());

        // Italian Dessert
        rb4.setOnAction(e -> updateSelectedItems());
        rb5.setOnAction(e -> updateSelectedItems());
        rb6.setOnAction(e -> updateSelectedItems());

        // Drinks
        drinksCombo1.setOnAction(e -> updateSelectedItems());
        drinksCombo2.setOnAction(e -> updateSelectedItems());
    }

    private void updateSelectedItems() {
        selectedItems.clear();
        totalAmount = 0;

        // Basotho Dinner Items
        if (cb1.isSelected()) {
            selectedItems.add("Papa le Moroho - M45.00");
            totalAmount += PAPA_LE_MOROHO;
        }
        if (cb2.isSelected()) {
            selectedItems.add("Lijo tsa Setho - M55.00");
            totalAmount += LIJO_TSA_SETHO;
        }
        if (cb3.isSelected()) {
            selectedItems.add("Nama ea Khomo - M75.00");
            totalAmount += NAMA_EA_KHOMO;
        }

        // Italian Dinner Items
        if (cb4.isSelected()) {
            selectedItems.add("Pasta Carbonara - M85.00");
            totalAmount += PASTA_CARBONARA;
        }
        if (cb5.isSelected()) {
            selectedItems.add("Pizza Margherita - M95.00");
            totalAmount += PIZZA_MARGHERITA;
        }
        if (cb6.isSelected()) {
            selectedItems.add("Risotto - M110.00");
            totalAmount += RISOTTO;
        }

        // Basotho Dessert
        if (rb1.isSelected()) {
            selectedItems.add("Makoenya - M25.00");
            totalAmount += MAKOENYA;
        }
        if (rb2.isSelected()) {
            selectedItems.add("Likhobe - M30.00");
            totalAmount += LIKHOBE;
        }
        if (rb3.isSelected()) {
            selectedItems.add("Motoho - M20.00");
            totalAmount += MOTOHO;
        }

        // Italian Dessert
        if (rb4.isSelected()) {
            selectedItems.add("Tiramisu - M45.00");
            totalAmount += TIRAMISU;
        }
        if (rb5.isSelected()) {
            selectedItems.add("Panna Cotta - M40.00");
            totalAmount += PANNA_COTTA;
        }
        if (rb6.isSelected()) {
            selectedItems.add("Gelato - M35.00");
            totalAmount += GELATO;
        }

        // Basotho Drinks
        if (drinksCombo1.getValue() != null) {
            String drink = drinksCombo1.getValue();
            selectedItems.add(drink);
            totalAmount += extractPrice(drink);
        }

        // Italian Drinks
        if (drinksCombo2.getValue() != null) {
            String drink = drinksCombo2.getValue();
            selectedItems.add(drink);
            totalAmount += extractPrice(drink);
        }

        updateTotal();
    }

    private double extractPrice(String item) {
        try {
            String[] parts = item.split("M");
            if (parts.length > 1) {
                return Double.parseDouble(parts[1].trim()) * 100;
            }
        } catch (Exception e) {
            System.err.println("Error extracting price: " + e.getMessage());
        }
        return 0;
    }

    private void updateTotal() {
        totalLabel.setText("M" + String.format("%.2f", totalAmount / 100));
    }

    private void calculateChange() {
        try {
            String cashText = cashPaidField.getText().trim();
            if (cashText.isEmpty()) {
                showAlert("Error", "Please enter the cash amount tendered.");
                return;
            }

            double cash = Double.parseDouble(cashText) * 100;
            double change = cash - totalAmount;

            if (change >= 0) {
                changeLabel.setText("M" + String.format("%.2f", change / 100));
                transactionTimeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

                // Save transaction
                model.saveTransaction(totalAmount / 100, cash / 100, change / 100);

                // Show transaction complete dialog
                showTransactionComplete(cash / 100, change / 100);
            } else {
                showAlert("Insufficient Payment", "The cash amount is less than the total. Please add M" + String.format("%.2f", Math.abs(change / 100)) + " more.");
            }
        } catch (NumberFormatException ex) {
            showAlert("Invalid Input", "Please enter a valid cash amount.");
        }
    }

    private void showTransactionComplete(double cash, double change) {
        StringBuilder receipt = new StringBuilder();
        receipt.append("=== TRANSACTION COMPLETE ===\n\n");
        receipt.append("Items Ordered:\n");
        for (String item : selectedItems) {
            receipt.append("  - ").append(item).append("\n");
        }
        receipt.append("\n");
        receipt.append("Total: M").append(String.format("%.2f", totalAmount / 100)).append("\n");
        receipt.append("Cash: M").append(String.format("%.2f", cash)).append("\n");
        receipt.append("Change: M").append(String.format("%.2f", change)).append("\n");
        receipt.append("\nThank you for dining with us!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Transaction Complete");
        alert.setHeaderText("Payment Successful!");
        alert.setContentText(receipt.toString());
        alert.showAndWait();
    }

    private void resetOrder() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Reset");
        confirm.setHeaderText("Reset Order?");
        confirm.setContentText("Are you sure you want to clear all items?");

        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            // Clear all checkboxes
            cb1.setSelected(false);
            cb2.setSelected(false);
            cb3.setSelected(false);
            cb4.setSelected(false);
            cb5.setSelected(false);
            cb6.setSelected(false);

            // Clear all radio buttons
            rb1.setSelected(false);
            rb2.setSelected(false);
            rb3.setSelected(false);
            rb4.setSelected(false);
            rb5.setSelected(false);
            rb6.setSelected(false);

            // Clear combo boxes
            drinksCombo1.setValue(null);
            drinksCombo2.setValue(null);

            // Clear fields
            cashPaidField.clear();
            totalLabel.setText("M0.00");
            changeLabel.setText("M0.00");
            transactionTimeLabel.setText("--:--:--");

            // Clear selected items list
            selectedItems.clear();
            totalAmount = 0;

            showAlert("Reset", "Order has been cleared.");
        }
    }

    private void confirmExit() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Exit");
        confirm.setHeaderText("Exit Application?");
        confirm.setContentText("Are you sure you want to exit?");

        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            System.exit(0);
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}