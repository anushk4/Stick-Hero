package com.example.sample;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class SceneController {
    @FXML
    public Label scoreLabel;
    @FXML
    public Label highLabel;
    @FXML
    private Line stickLine;
    private Stick stick;
    public static boolean soundPlay = false;
    private Hero hero;
    private static int gamePaused = 0;
    private Timeline timeline;

    @FXML
    private Button scoreButton;
    @FXML
    public Line cancel;

    @FXML
    private Circle circle1;

    @FXML
    private Circle circle2;

    @FXML
    private ImageView myHero;

    @FXML
    private Rectangle nextPillar;

    private int stickDown = 0;

    @FXML
    private Button cherryCount;

    private Pillar targetPillar;
    private Stage stage;
    private Scene scene;

    @FXML
    private Rectangle homeRectangle;
    @FXML
    private Rectangle resumeRectangle;
    @FXML
    private Rectangle restartRectangle;
    @FXML
    private Rectangle saveRectangle;
    @FXML
    private ImageView homeIcon;
    @FXML
    private ImageView saveIcon;
    @FXML
    private ImageView restartIcon;
    @FXML
    private ImageView pauseGame;
    @FXML
    private Polygon resumeIcon;
    @FXML
    private Button restartButton;
    @FXML
    private ImageView pauseIcon;
    @FXML
    private Button pauseButton;
    @FXML
    private Rectangle yesCherry1;
    @FXML
    private Label yesCherry2;
    @FXML
    private Rectangle yesCherry3;
    @FXML
    private Button yesCherry4;
    @FXML
    private Label yesCherry5;
    @FXML
    private Rectangle yesCherry6;
    @FXML
    private Button yesCherry7;
    @FXML
    private Label yesCherry8;
    @FXML
    private Rectangle noCherries1;
    @FXML
    private Label noCherries2;
    @FXML
    private Label noCherries3;
    @FXML
    private Rectangle noCherries4;
    @FXML
    private Button noCherries5;
    @FXML
    private Label noCherries6;
    public static int gameLoaded = 0;
    public static int gameResumed = 0;
    private static MediaPlayer mediaPlayer = null;

    public static int stickno = 0;
    public static int pillarno = 0;

    @FXML
    private Rectangle prevPillar;
    public static int cherryGenerate = 0;
    private static int count = 0;

    private Pillar initialPillar;
    public static ArrayList<Pillar> pillars = new ArrayList<>();
    public static ArrayList<Rectangle> rectangles = new ArrayList<>();
    public static ArrayList<Stick> sticks = new ArrayList<>();
    public static ArrayList<Line> sticklines = new ArrayList<>();
    public static ArrayList<ImageView> cherries = new ArrayList<>();

    @FXML
    public void initialize() {
        // Initialize the Timeline
        hero = new Hero(1, 1.0, 0, 0);
        stick = new Stick(0, 0);
        sticks.add(stick);
        sticklines.add(0, stickLine);
        floatHeroImage();
        timeline = new Timeline(new KeyFrame(javafx.util.Duration.millis(10), this::increaseStickLength));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void switchToPlayScreen(ActionEvent event) throws IOException {
        stickno = 0;
        pillarno = 0;
        sticks.clear();
        sticklines.clear();
        rectangles.clear();
        cherries.clear();
        pillars.clear();
        stickDown = 0;
        gamePaused = 0;
        gameResumed = 0;
        MainMenu.play(event);
    }

    public void increaseCircle(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), circle1);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.play();
    }

    public void increaseCircle1(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), circle2);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.play();
    }

    public void revive(MouseEvent event) throws IOException {
        int total_cherries;
        FileInputStream in1 = null;
        try {
            in1 = new FileInputStream("cherry.txt");
            total_cherries = Math.max(in1.read(), 0);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (total_cherries < 5) {
            noCherries1.toFront();
            noCherries2.toFront();
            noCherries3.toFront();
            noCherries4.toFront();
            noCherries5.toFront();
            noCherries6.toFront();
        } else {
            yesCherry1.toFront();
            yesCherry2.toFront();
            yesCherry3.toFront();
            yesCherry4.toFront();
            yesCherry5.toFront();
            yesCherry6.toFront();
            yesCherry7.toFront();
            yesCherry8.toFront();
        }
    }

    public void notEnoughCherries(MouseEvent event) throws IOException {
        noCherries1.toBack();
        noCherries2.toBack();
        noCherries3.toBack();
        noCherries4.toBack();
        noCherries5.toBack();
        noCherries6.toBack();
    }

    public void rejectRevival(MouseEvent event) throws IOException {
        yesCherry1.toBack();
        yesCherry2.toBack();
        yesCherry3.toBack();
        yesCherry4.toBack();
        yesCherry5.toBack();
        yesCherry6.toBack();
        yesCherry7.toBack();
        yesCherry8.toBack();
    }

    public void playAfterRevival(MouseEvent event) throws IOException {
        int total_cherries;
        FileInputStream in1 = null;
        try {
            in1 = new FileInputStream("cherry.txt");
            total_cherries = Math.max(in1.read(), 0);
            FileOutputStream out1 = null;
            out1 = new FileOutputStream("cherry.txt");
            out1.write(total_cherries - 5);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stickno = 0;
        pillarno = 0;
        sticks.clear();
        sticklines.clear();
        rectangles.clear();
        cherries.clear();
        pillars.clear();
        stickDown = 0;
        gamePaused = 0;
        gameResumed = 0;
        int score;
        FileInputStream restore = null;
        try {
            restore = new FileInputStream("score.txt");
            score = restore.read();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            restore.close();
        }
        FileOutputStream saveScore = null;
        try {
            saveScore = new FileOutputStream("score.txt");
            saveScore.write("".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            saveScore.close();
        }
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainMenu.class.getResource("mainscreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        Button scoreButton = (Button) root.lookup("#scoreButton");
        scoreButton.setText(String.valueOf(score));
        stage.setScene(scene);
        stage.show();
    }

    public void saveGame(MouseEvent event) throws IOException {
        PauseMenu pause = PauseMenu.getInstance();
        pause.savegame(hero);
    }

    public void decreaseCircle(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), circle1);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }

    public void decreaseCircle1(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), circle2);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }

    public void loadSaved(MouseEvent event) throws IOException {
        FileInputStream in = null;
        int state = 0;
        try {
            in = new FileInputStream("gamestate.txt");
            state = in.read();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            in.close();
        }
        if (state != -1) {
            gameLoaded = 1;
            stickno = 0;
            pillarno = 0;
            sticks.clear();
            sticklines.clear();
            rectangles.clear();
            cherries.clear();
            pillars.clear();
            stickDown = 0;
            gamePaused = 0;
            gameResumed = 0;
            Parent root = FXMLLoader.load(Objects.requireNonNull(MainMenu.class.getResource("mainscreen.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            ObjectInputStream in3 = null;
            Hero hero = null;
            try {
                in3 = new ObjectInputStream(new FileInputStream("gamestate.txt"));
                hero = (Hero) in3.readObject();

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            Button cherryCount = (Button) root.lookup("#cherryCount");
            Button scoreButton = (Button) root.lookup("#scoreButton");
            cherryCount.setText(String.valueOf(hero.getCherries()));
            scoreButton.setText(String.valueOf(hero.getScore()));
        } else {
            Alert savedAlert = new Alert(Alert.AlertType.INFORMATION);
            savedAlert.setTitle("No Loaded Games");
            savedAlert.setHeaderText(null);
            savedAlert.setContentText("No games have been saved yet");

            // Show alert for 5 seconds
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ae -> savedAlert.hide()));
            timeline.setCycleCount(1);
            timeline.play();
            savedAlert.showAndWait();
        }
    }

    public void resumeGame(MouseEvent event) throws IOException {
        gameResumed = 1;
        gamePaused = 0;
        homeRectangle.setOpacity(0);
        resumeRectangle.setOpacity(0);
        restartRectangle.setOpacity(0);
        saveRectangle.setOpacity(0);
        homeIcon.setOpacity(0);
        saveIcon.setOpacity(0);
        restartIcon.setOpacity(0);
        pauseGame.setOpacity(0);
        resumeIcon.setOpacity(0);
        restartButton.setOpacity(0);
        homeRectangle.toBack();
        resumeRectangle.toBack();
        restartRectangle.toBack();
        saveRectangle.toBack();
        homeIcon.toBack();
        saveIcon.toBack();
        restartIcon.toBack();
        pauseGame.toBack();
        resumeIcon.toBack();
        restartButton.toBack();
        pauseIcon.setOpacity(1);
        myHero.setOpacity(1);
        scoreButton.setOpacity(1);
        pauseButton.toFront();
        pauseIcon.toFront();
        if (rectangles.size() >= 2) {
            rectangles.get(rectangles.size() - 2).toFront();
            rectangles.get(rectangles.size() - 1).toFront();
        } else {
            prevPillar.toFront();
            nextPillar.toFront();
        }
        myHero.toFront();
        scoreButton.toFront();
        for (Line s : sticklines) {
            s.toFront();
        }
        if (cherryGenerate == 1) {
            cherries.get(cherries.size() - 1).toFront();
        }
        for (Rectangle r : rectangles) {
            r.toFront();
        }
    }

    public void restartGame(MouseEvent event) throws IOException {
        gamePaused = 0;
        stickno = 0;
        pillarno = 0;
        sticks.clear();
        sticklines.clear();
        rectangles.clear();
        cherries.clear();
        pillars.clear();
        stickDown = 0;
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainMenu.class.getResource("mainscreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToHomeScreen(MouseEvent event) throws IOException {
        PauseMenu pause = PauseMenu.getInstance();
        pause.returntohome(event,cancel);
    }

    public void playsound(MouseEvent event){
        soundPlay = !soundPlay;
        if (count < 1){
            String soundFile = "gameSound.mp3" ;
            Media media = new Media(new File(soundFile).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
            count++;
        }
        if (soundPlay){
            mediaPlayer.setVolume(1);
            cancel.setOpacity(0);
        }else{
            mediaPlayer.setVolume(0);
            cancel.setOpacity(1);
        }
    }

    public void switchToPauseScreen(MouseEvent event) throws IOException {
        gamePaused = 1;
        homeRectangle.setOpacity(1);
        resumeRectangle.setOpacity(1);
        restartRectangle.setOpacity(1);
        saveRectangle.setOpacity(1);
        homeIcon.setOpacity(1);
        saveIcon.setOpacity(1);
        restartIcon.setOpacity(1);
        pauseGame.setOpacity(1);
        resumeIcon.setOpacity(1);
        homeRectangle.toFront();
        resumeRectangle.toFront();
        restartRectangle.toFront();
        saveRectangle.toFront();
        homeIcon.toFront();
        saveIcon.toFront();
        restartIcon.toFront();
        pauseGame.toFront();
        resumeIcon.toFront();
        restartButton.toFront();
        pauseIcon.setOpacity(0);
        myHero.setOpacity(0);
        scoreButton.setOpacity(0);
        pauseIcon.toBack();
        if (rectangles.size() >= 2) {
            rectangles.get(rectangles.size() - 2).toBack();
            rectangles.get(rectangles.size() - 1).toBack();
        } else {
            prevPillar.toBack();
            nextPillar.toBack();
        }
        myHero.toBack();
        scoreButton.toBack();
        for (Line s : sticklines) {
            s.toBack();
        }
        for (Rectangle r : rectangles) {
            r.toBack();
        }
        if (cherryGenerate == 1) {
            cherries.get(cherries.size() - 1).toBack();
        }
    }

    @FXML
    public void handleMousePressed(MouseEvent event) throws NegativeRotationException {
        if (gamePaused == 1) {
            return;
        }
        if (gameResumed == 1) {
            gameResumed = 0;
            sticks.get(stickno).setRotation(0);
            stickDown = 0;
            return;
        }
        if (sticks.get(stickno).getRotation() == 0) {
            sticklines.get(stickno).setOpacity(1);
            stickDown = 1;
            timeline.play();
        } else if (sticks.get(stickno).getRotation() == -90) {
            stickDown = 0;
            hero.flipHero(myHero);
        }
    }

    public void floatHeroImage() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), myHero);
        translateTransition.setByY(2);
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }

    @FXML
    public void handleMouseReleased(MouseEvent event) throws InterruptedException {
        if (stickDown == 0) {
            return;
        }
        timeline.stop();
        if (pillarno == 0) {
            rectangles.add(prevPillar);
            rectangles.add(nextPillar);
            double width = nextPillar.getWidth();
            double prevDistance = nextPillar.getLayoutX() - (prevPillar.getLayoutX() + prevPillar.getWidth());
            targetPillar = new Pillar(width, width / 2, prevDistance);
            pillars.add(initialPillar);
            pillars.add(targetPillar);
        }
        double stickLength = Math.sqrt(Math.pow(sticklines.get(stickno).getEndX() - sticklines.get(stickno).getStartX(), 2) + Math.pow(sticklines.get(stickno).getEndY() - sticklines.get(stickno).getStartY(), 2));
        sticks.get(stickno).setLength(stickLength);
        sticks.get(stickno).rotateStick(sticklines.get(stickno), hero, myHero, pillars.get(pillarno + 1), rectangles.get(pillarno + 1), rectangles.get(pillarno), scoreButton, cherryCount);
    }

    public void increaseStickLength(ActionEvent event) {
        sticks.get(stickno).increaseLength(sticklines.get(stickno));
    }
}