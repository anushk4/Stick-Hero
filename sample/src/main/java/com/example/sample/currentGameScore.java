package com.example.sample;

public class currentGameScore implements ScoreBoard {

    @Override
    public int displayScore(Hero hero) {
        return hero.getScore();
    }

    @Override
    public int displayCherries(Hero hero) {
        return hero.getCherries();
    }
}
