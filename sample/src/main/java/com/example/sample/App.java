package com.example.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

// Main Class
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Loading the main screen
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Stick Hero");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}