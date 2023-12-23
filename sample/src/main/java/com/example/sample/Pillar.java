package com.example.sample;

public class Pillar {
    private double width;
    private double redCentre;
    private double distanceFromPrev;
    private static float length = 10;

    public Pillar(double width, double redCentre, double distanceFromPrev) {
        this.width = width;
        this.redCentre = redCentre;
        this.distanceFromPrev = distanceFromPrev;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public double getRedCentre() {
        return redCentre;
    }

    public void setRedCentre(float redCentre) {
        this.redCentre = redCentre;
    }

    public double getDistanceFromPrev() {
        return distanceFromPrev;
    }

    public void setDistanceFromPrev(float distanceFromPrev) {
        this.distanceFromPrev = distanceFromPrev;
    }

    public static float getLength() {
        return length;
    }
}
