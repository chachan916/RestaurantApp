package com.restaurant.restaurantapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/restaurant/restaurantapp/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 750);
        stage.setTitle("La Famiglia Di Raven");
        stage.setScene(scene);
        stage.show();
    }

}