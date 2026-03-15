package com.restaurant.restaurantapp;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RestaurantController {

    @FXML private CheckBox cb1, cb2, cb3;
    @FXML private RadioButton rb1, rb2, rb3;
    @FXML private ComboBox<String> drinksCombo;
    @FXML private TextField cashPaidField;
    @FXML private Label totalLabel;
    @FXML private Label changeLabel;
    @FXML private Label transactionTimeLabel;
    @FXML private Button totalBtn, changeBtn, resetBtn, exitBtn;

    private RestaurantModel model;
    private double totalAmount = 0;

    @FXML
    public void initialize() {
        System.out.println("Restaurant Controller Started");
        model = new RestaurantModel();

        drinksCombo.setItems(FXCollections.observableArrayList(
                "House Wine - 800",
                "Premium Wine - 1200",
                "Beer - 600",
                "Juice - 300",
                "Water - 100",
                "Soft Drink - 250",
                "Cocktail - 900"
        ));

        ToggleGroup dessertGroup = new ToggleGroup();
        rb1.setToggleGroup(dessertGroup);
        rb2.setToggleGroup(dessertGroup);
        rb3.setToggleGroup(dessertGroup);

        cb1.setOnAction(e -> updateTotal());
        cb2.setOnAction(e -> updateTotal());
        cb3.setOnAction(e -> updateTotal());
        rb1.setOnAction(e -> updateTotal());
        rb2.setOnAction(e -> updateTotal());
        rb3.setOnAction(e -> updateTotal());
        drinksCombo.setOnAction(e -> updateTotal());

        totalBtn.setOnAction(e -> {
            updateTotal();
            showAlert("Total", "Total: M" + totalAmount/100);
        });

        changeBtn.setOnAction(e -> {
            try {
                double cash = Double.parseDouble(cashPaidField.getText()) * 100;
                double change = cash - totalAmount;
                if (change >= 0) {
                    changeLabel.setText("M" + String.format("%.2f", change/100));
                    transactionTimeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                    model.saveTransaction(totalAmount/100, cash/100, change/100);
                    showAlert("Success", "Change: M" + change/100);
                } else {
                    showAlert("Error", "Insufficient payment");
                }
            } catch (Exception ex) {
                showAlert("Error", "Invalid amount");
            }
        });

        resetBtn.setOnAction(e -> {
            cb1.setSelected(false); cb2.setSelected(false); cb3.setSelected(false);
            rb1.setSelected(false); rb2.setSelected(false); rb3.setSelected(false);
            drinksCombo.setValue(null); cashPaidField.clear();
            totalLabel.setText("M0.00"); changeLabel.setText("M0.00");
            transactionTimeLabel.setText("--:--:--"); totalAmount = 0;
            showAlert("Reset", "Form cleared");
        });

        exitBtn.setOnAction(e -> System.exit(0));
    }

    private void updateTotal() {
        totalAmount = 0;
        if (cb1.isSelected()) totalAmount += 500;
        if (cb2.isSelected()) totalAmount += 450;
        if (cb3.isSelected()) totalAmount += 550;
        if (rb1.isSelected()) totalAmount += 300;
        if (rb2.isSelected()) totalAmount += 400;
        if (rb3.isSelected()) totalAmount += 250;
        if (drinksCombo.getValue() != null) {
            String[] parts = drinksCombo.getValue().split(" - ");
            totalAmount += Double.parseDouble(parts[1]);
        }
        totalLabel.setText("M" + String.format("%.2f", totalAmount/100));
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title); alert.setContentText(msg); alert.show();
    }
}