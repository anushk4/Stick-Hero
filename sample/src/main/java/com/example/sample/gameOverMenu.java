package com.example.sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class gameOverMenu extends SideMenu {

    private gameOverScore scoreBoard;
    private static Stage stage;
    private static Scene scene;

    public gameOverMenu(gameOverScore scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    @Override
    public void returntohome(MouseEvent event, Line cancel) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        if (SceneController.soundPlay){
            cancel.setOpacity(0);
        }else{
            cancel.setOpacity(1);
        }
    }

    public gameOverScore getscoreboard() {
        return this.scoreBoard;
    }
}
