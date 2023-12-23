package com.example.sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class PauseMenu extends SideMenu {

    private static Stage stage;
    private static Scene scene;
    private static PauseMenu gen = null;

    private PauseMenu() {
    }

    // Singleton Design Pattern
    public static PauseMenu getInstance() {
        if (gen == null) {
            gen = new PauseMenu();
        }
        return gen;
    }

    @Override
    public void returntohome(MouseEvent event, Line cancel) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Line cancel1 = (Line) root.lookup("#cancel");
        if (SceneController.soundPlay){
            cancel1.setOpacity(0);
        }else{
            cancel1.setOpacity(1);
        }
    }

    public void savegame(Hero hero) throws IOException {
        Alert savedAlert = new Alert(Alert.AlertType.INFORMATION);
        savedAlert.setTitle("Game Saved");
        savedAlert.setHeaderText(null);
        savedAlert.setContentText("Your game has been saved.");
        ObjectOutputStream out3 = null;
        try {
            out3 = new ObjectOutputStream(new FileOutputStream("gamestate.txt"));
            out3.writeObject(hero);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            out3.close();
        }

        // Show alert for 5 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ae -> savedAlert.hide()));
        timeline.setCycleCount(1);
        timeline.play();

        savedAlert.showAndWait();
    }
}
