package com.restaurant.restaurantapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class RegistrationController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button registerBtn;
    @FXML private Button backBtn;

    @FXML
    public void initialize() {
        System.out.println("✅ Registration Controller Started");

        registerBtn.setOnAction(e -> handleRegistration());
        backBtn.setOnAction(e -> goBackToLogin());
    }

    private void handleRegistration() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String confirm = confirmPasswordField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            showAlert("Error", "All fields required");
            return;
        }

        if (!password.equals(confirm)) {
            showAlert("Error", "Passwords do not match");
            return;
        }

        if (UserDatabase.registerUser(username, password)) {
            showAlert("Success", "Account created! Please login.");
            goBackToLogin();
        } else {
            showAlert("Error", "Username already exists");
        }
    }

    private void goBackToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/restaurant/restaurantapp/login-view.fxml"));
            Scene scene = new Scene(loader.load(), 650, 750);
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Cannot load login screen");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}