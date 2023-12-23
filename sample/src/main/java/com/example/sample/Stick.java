package com.example.sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.Serializable;

public class Stick implements Serializable {
    static final long serialVersionUID = 20L;
    private double length;
    private float rotation;
    public Timeline rotationTimeline;

    public Stick(float length, float rotation) {
        this.length = length;
        this.rotation = rotation;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void increaseLength(Line stickLine) {
        stickLine.setEndY(stickLine.getEndY() - 2);
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) throws NegativeRotationException {
        if (rotation > 0) {
            throw new NegativeRotationException("Stick can't be rotated anti clockwise");
        }
        this.rotation = rotation;
    }

    public void rotateStick(Line stickLine, Hero hero, ImageView myHero, Pillar targetPillar, Rectangle target, Rectangle prevPillar, Button scoreButton, Button cherryCount) {
        double deltaX = stickLine.getEndX() - stickLine.getStartX();
        double deltaY = stickLine.getEndY() - stickLine.getStartY();
        double angle = Math.atan2(deltaY, deltaX);

        // Convert the angle from radians to degrees and negate it to rotate to the right
        double degrees = -Math.toDegrees(angle);

        // Apply rotation transformation gradually
        rotationTimeline = new Timeline(
                new KeyFrame(Duration.millis(5), e -> {
                    try {
                        rotateStick(degrees / 100, stickLine);
                    } catch (NegativeRotationException ex) {
                        throw new RuntimeException(ex);
                    }
                })
        );
        rotationTimeline.setCycleCount(100);
        rotationTimeline.setOnFinished(e -> hero.move(myHero, stickLine, targetPillar, target, prevPillar, scoreButton, cherryCount));
        rotationTimeline.play();
    }

    private void rotateStick(double angle, Line stickLine) throws NegativeRotationException {
        Rotate rotate = new Rotate(angle, stickLine.getStartX(), stickLine.getStartY());
        stickLine.getTransforms().add(rotate);
        this.setRotation(-90);
    }
}
