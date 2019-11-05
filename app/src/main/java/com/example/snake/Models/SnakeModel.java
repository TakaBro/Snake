package com.example.snake.Models;

import android.util.Log;

import java.util.Random;

public class SnakeModel {
    private String name;
    private int length;
    private int score;
    private boolean dead;

    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void addScore(){
        this.score++;
    }
    public boolean getDead() {
        return dead;
    }
    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
