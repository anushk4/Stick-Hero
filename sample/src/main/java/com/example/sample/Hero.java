package com.example.sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.Random;

public class Hero implements Serializable {
    static final long serialVersionUID = 42L;
    private int state;
    private double speed;
    private int cherries;
    private int score;
    private Stick stick;
    private int cherriesCollected = 0;
    private final transient Timeline[] moveTimeline = new Timeline[1];
    public transient TranslateTransition cherryExit, shiftTransition, heroTransition, stickTransition, prevTransition, transition, cherryTransition;
    public transient Timeline fallTimeline;

    public void collectCherries(int cherries) throws NegativeCherryException {
        if (cherries < 0) {
            throw new NegativeCherryException("Cherries can't be negative");
        }
        this.cherries = cherries;
    }

    public Hero(int state, double speed, int cherries, int score) {
        this.state = state;
        this.speed = speed;
        this.cherries = cherries;
        this.score = score;
        this.stick = new Stick(0, 90);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) throws NegativeScoreException {
        if (score < 0) {
            throw new NegativeScoreException("Score can't be negative");
        }
        this.score = score;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) throws NegativeSpeedException {
        if (speed < 0) {
            throw new NegativeSpeedException("Speed can't be negative");
        }
        this.speed = speed;
    }

    public int getCherries() {
        return cherries;
    }

    public void flipHero(ImageView myHero) {
        if (this.state == 1) {
            double originalBottomY = myHero.getFitHeight();
            myHero.setScaleY(myHero.getScaleY() * -1);
            myHero.setLayoutY(myHero.getLayoutY() + originalBottomY);
            this.state = -1;
        } else if (this.state == -1) {
            double originalBottomY = myHero.getFitHeight();
            myHero.setScaleY(myHero.getScaleY() * -1);
            myHero.setLayoutY(myHero.getLayoutY() - originalBottomY);
            this.state = 1;
        }
    }

    public void fall(ImageView myHero) throws IOException {
        double fallDistance = 300.0;
        double speed = 2.0;
        double targetY = myHero.getY() + fallDistance;
        double distanceToFall = Math.abs(targetY - myHero.getY());
        fallTimeline = new Timeline(
                new KeyFrame(Duration.millis(5), e -> {
                    double newY = myHero.getY() + speed;
                    myHero.setY(newY);
                })
        );
        FileInputStream s = null;
        int state = 0;
        try {
            s = new FileInputStream("gamestate.txt");
            state = s.read();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            s.close();
        }
        if (state != -1 && SceneController.gameLoaded == 1) {
            SceneController.gameLoaded = 0;
            ObjectInputStream in3 = null;
            Hero hero = null;
            try {
                in3 = new ObjectInputStream(new FileInputStream("gamestate.txt"));
                hero = (Hero) in3.readObject();

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            this.cherries += hero.getCherries();
            this.score += hero.getScore();
        }
        int c;
        FileInputStream in = null;
        try {
            in = new FileInputStream("high_score.txt");
            c = Math.max(in.read(), 0);
            if (this.score > c) {
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream("high_score.txt");
                    out.write(this.score);
                    c = this.score;
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    out.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            in.close();
        }
        int finalC1 = c;
        fallTimeline.setOnFinished(event -> {
            FileOutputStream saveScore = null;
            try {
                saveScore = new FileOutputStream("score.txt");
                saveScore.write(this.score);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int total_cherries;
            FileInputStream in1 = null;
            try {
                in1 = new FileInputStream("cherry.txt");
                int a = Math.max(in1.read(), 0);
                total_cherries = a + this.cherries;
                FileOutputStream out1 = null;
                out1 = new FileOutputStream("cherry.txt");
                out1.write(total_cherries);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Load the gameover.fxml scene
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("gameover.fxml"));
                Parent gameoverRoot = loader.load();
                Scene gameoverScene = new Scene(gameoverRoot);
                Label scoreLabel = (Label) gameoverRoot.lookup("#scoreLabel");
                Label highLabel = (Label) gameoverRoot.lookup("#highLabel");
                Button cherryCount = (Button) gameoverRoot.lookup("#cherryCount");
                scoreLabel.setText(String.valueOf(score));
                highLabel.setText(String.valueOf(finalC1));
                cherryCount.setText(String.valueOf(total_cherries));
                Stage currentStage = (Stage) myHero.getScene().getWindow();
                currentStage.setScene(gameoverScene);
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        int cycles = (int) (distanceToFall / speed);
        fallTimeline.setCycleCount(cycles);
        fallTimeline.play();
    }

    public void move(ImageView myHero, Line stickLine, Pillar targetPillar, Rectangle target, Rectangle prevPillar, Button scoreButton, Button cherryCount) {
        double stickLength = Math.sqrt(Math.pow(stickLine.getEndX() - stickLine.getStartX(), 2) + Math.pow(stickLine.getEndY() - stickLine.getStartY(), 2));
        double targetX = myHero.getX() + stickLength + 15;
        double currentX = myHero.getX();
        double distanceToMove = Math.abs(targetX - currentX);

       // Use of Timeline for moving the hero
        moveTimeline[0] = new Timeline(
                new KeyFrame(Duration.millis(5), e -> {
                    if (myHero.getBoundsInParent().intersects(target.getBoundsInParent()) && this.state == -1) {
                        try {
                            this.fall(myHero);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        // Stopping the timeline
                        moveTimeline[0].stop();
                    } else {
                        double newX = myHero.getX() + (targetX > currentX ? speed : -speed);
                        myHero.setX(newX);
                    }
                    if (!SceneController.cherries.isEmpty()) {
                        ImageView collectedCherry = SceneController.cherries.get(SceneController.cherries.size() - 1);
                        if (this.state == -1 && myHero.getBoundsInParent().intersects(collectedCherry.getBoundsInParent())) {
                            cherriesCollected = 1;
                            collectedCherry.setX(collectedCherry.getX() - (targetPillar.getDistanceFromPrev() + targetPillar.getWidth() + SceneController.rectangles.get(0).getWidth()));
                        }
                    }
                })
        );

        if (stickLength < targetPillar.getDistanceFromPrev() || stickLength > (targetPillar.getDistanceFromPrev() + targetPillar.getWidth())) {
            moveTimeline[0].setOnFinished(event -> {
                try {
                    fall(myHero);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            int cycles = (int) (distanceToMove / this.speed);
            moveTimeline[0].setCycleCount(cycles);
            moveTimeline[0].play();
        } else {
            double extramove = Math.abs(targetPillar.getWidth() - (stickLength - targetPillar.getDistanceFromPrev())) - 15;
            int cycles = (int) ((distanceToMove + extramove) / this.speed);
            moveTimeline[0].setCycleCount(cycles);
            moveTimeline[0].setOnFinished(event -> {
                scoreButton.setText(String.valueOf(Integer.parseInt(scoreButton.getText()) + 1));
                this.score += 1;
                if (cherriesCollected == 0 && !SceneController.cherries.isEmpty()) {
                    ImageView uncollectedCherry = SceneController.cherries.get(SceneController.cherries.size() - 1);
                    cherryExit = new TranslateTransition(Duration.millis(500), uncollectedCherry);
                    double cherryDistance = targetPillar.getDistanceFromPrev() + targetPillar.getWidth() + SceneController.rectangles.get(0).getWidth();
                    cherryExit.setByX(-cherryDistance);
                    cherryExit.play();
                } else if (cherriesCollected == 1) {
                    cherryCount.setText(String.valueOf(Integer.parseInt(cherryCount.getText()) + 1));
                    this.cherries += 1;
                    cherriesCollected = 0;
                }
                shiftTransition = new TranslateTransition(Duration.millis(500), target);
                double translationDistance = targetPillar.getDistanceFromPrev() + targetPillar.getWidth();
                shiftTransition.setByX(-translationDistance);
                shiftTransition.play();
                heroTransition = new TranslateTransition(Duration.millis(500), myHero);
                double heroDistance = targetPillar.getDistanceFromPrev() + targetPillar.getWidth();
                heroTransition.setByX(-heroDistance);
                heroTransition.play();
                stickTransition = new TranslateTransition(Duration.millis(500), stickLine);
                double stickDistance = targetPillar.getDistanceFromPrev() + targetPillar.getWidth();
                stickTransition.setByX(-stickDistance);
                stickTransition.play();
                prevTransition = new TranslateTransition(Duration.millis(500), prevPillar);
                double prevDistance = targetPillar.getDistanceFromPrev() + targetPillar.getWidth();
                prevTransition.setByX(-prevDistance);
                prevTransition.play();
                AnchorPane anchor = (AnchorPane) myHero.getScene().lookup("#anchor");
                if (anchor != null) {
                    double width = 30 + Math.random() * 120;
                    Rectangle nextPillar = (Rectangle) Factory.getShape("Rectangle");
                    nextPillar.setWidth(width);
                    nextPillar.setHeight(200);
                    nextPillar.setLayoutX(anchor.getWidth());
                    nextPillar.setLayoutY(target.getLayoutY());
                    anchor.getChildren().add(nextPillar);
                    SceneController.rectangles.add(nextPillar);
                    SceneController.pillarno++;
                    transition = new TranslateTransition(Duration.millis(500), nextPillar);
                    double extra = 9 + Math.random() * 180;
                    SceneController.cherryGenerate = 0;
                    Random random = new Random();
                    int generateCherry = random.nextInt(2);
                    if (extra > 50 && generateCherry == 1) {
                        Image cherryImage = new Image("cherry.png");
                        ImageView cherry = new ImageView(cherryImage);
                        anchor.getChildren().add(cherry);
                        cherry.setFitHeight(25);
                        cherry.setFitWidth(27);
                        cherry.setLayoutX(anchor.getWidth());
                        cherry.setLayoutY(220);
                        double cherryExtra = 18 + Math.random() * (extra - 18);
                        cherryTransition = new TranslateTransition(Duration.millis(500), cherry);
                        cherryTransition.setToX(-(anchor.getWidth() - (SceneController.rectangles.get(0).getWidth() + cherryExtra) + 25));
                        cherryTransition.play();
                        SceneController.cherries.add(cherry);
                        SceneController.cherryGenerate = 1;
                    }
                    transition.setToX(-(anchor.getWidth() - (SceneController.rectangles.get(0).getWidth() + extra)));
                    Pillar newPillar = new Pillar(width, width / 2, extra);
                    SceneController.pillars.add(newPillar);
                    transition.play();
                    Stick stick = new Stick(0, 0);
                    Line line = (Line) Factory.getShape("Line");
                    line.setStartX(SceneController.rectangles.get(0).getWidth() - 5);
                    line.setStartY(target.getLayoutY());
                    line.setEndX(SceneController.rectangles.get(0).getWidth() - 5);
                    line.setEndY(target.getLayoutY() - 15);
                    line.setOpacity(0);
                    line.setStrokeWidth(4.0);
                    anchor.getChildren().add(line);
                    SceneController.stickno++;
                    SceneController.sticklines.add(SceneController.stickno, line);
                    SceneController.sticks.add(stick);

                } else {
                    System.out.println("Anchor is null");
                }
            });
            moveTimeline[0].play();
        }
    }
}
