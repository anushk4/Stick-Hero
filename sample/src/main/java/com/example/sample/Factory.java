package com.example.sample;

import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

// Factory Design Method
public class Factory {
    public static Shape getShape(String type) {
        if (type.equals("Rectangle")) {
            return new Rectangle();
        } else if (type.equals("Line")) {
            return new Line();
        } else {
            return null;
        }
    }
}
