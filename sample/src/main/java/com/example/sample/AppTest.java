package com.example.sample;

import org.junit.Test;

// Tests for the code
public class AppTest {
    @Test(expected = NegativeScoreException.class)
    public void testScore() throws NegativeScoreException {
        Hero hero = new Hero(1, 1.0, 0, 0);
        hero.setScore(-10);
    }

    @Test(expected = NegativeSpeedException.class)
    public void testSpeed() throws NegativeSpeedException {
        Hero hero = new Hero(1, 1.0, 0, 0);
        hero.setSpeed(-10);
    }

    @Test(expected = NegativeCherryException.class)
    public void testCherries() throws NegativeCherryException {
        Hero hero = new Hero(1, 1.0, 0, 0);
        hero.collectCherries(-1);
    }

    @Test(expected = NegativeRotationException.class)
    public void testRotate() throws NegativeRotationException {
        Stick stick = stick = new Stick(0, 0);
        stick.setRotation(90);
    }
}