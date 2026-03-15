package com.restaurant.restaurantapp;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class LoginController {

    @FXML private ImageView logoImageView;
    @FXML private VBox rootPane;
    @FXML private VBox loginCard;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginBtn;
    @FXML private Button signupBtn;
    @FXML private Button exitBtn;

    @FXML
    public void initialize() {

        FadeTransition fade = new FadeTransition(Duration.seconds(1.4), rootPane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        loginCard.setOpacity(0);
        loginCard.setTranslateY(40);

        TranslateTransition slide = new TranslateTransition(Duration.seconds(0.9), loginCard);
        slide.setToY(0);

        FadeTransition cardFade = new FadeTransition(Duration.seconds(0.9), loginCard);
        cardFade.setToValue(1);

        slide.play();
        cardFade.play();

        addHover(loginBtn);
        addHover(signupBtn);

        loginBtn.setOnAction(e -> handleLogin());
        signupBtn.setOnAction(e -> goToRegistration());
        exitBtn.setOnAction(e -> System.exit(0));
    }

    private void addHover(Button btn) {

        btn.setOnMouseEntered(e -> {
            ScaleTransition s = new ScaleTransition(Duration.millis(150), btn);
            s.setToX(1.07);
            s.setToY(1.07);
            s.play();
        });

        btn.setOnMouseExited(e -> {
            ScaleTransition s = new ScaleTransition(Duration.millis(150), btn);
            s.setToX(1);
            s.setToY(1);
            s.play();
        });
    }

    private void handleLogin() {

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if(username.isEmpty() || password.isEmpty()){
            showAlert("Validation Error","Please enter both username and password.");
            return;
        }

        if(UserDatabase.loginUser(username,password)){

            try{

                FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
                Scene scene = new Scene(loader.load(),860,680);

                Stage stage = (Stage) loginBtn.getScene().getWindow();
                stage.setTitle("La Famiglia Di Raven | Welcome " + username);
                stage.setScene(scene);
                stage.show();

            }catch(IOException ex){
                showAlert("Error","Failed to load main screen.");
            }

        }else{

            showAlert("Invalid Credentials",
                    "Incorrect username or password.");

            passwordField.clear();
            usernameField.requestFocus();
        }
    }

    private void goToRegistration(){

        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("registration-view.fxml"));
            Scene scene = new Scene(loader.load(),620,680);

            Stage stage = (Stage) signupBtn.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Create Account");
            stage.show();

        }catch(IOException e){

            showAlert("Error","Failed to load registration screen.");
        }
    }

    private void showAlert(String title,String message){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}