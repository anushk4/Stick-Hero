package com.example.sample;

public class gameOverScore implements ScoreBoard {

    private int bestScore;

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    @Override
    public int displayScore(Hero hero) {
        return hero.getScore();
    }

    @Override
    public int displayCherries(Hero hero) {
        return hero.getCherries();
    }
}
