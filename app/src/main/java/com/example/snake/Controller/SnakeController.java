package com.example.snake.Controller;

import com.example.snake.Models.SnakeModel;

public class SnakeController {

    private SnakeModel model;

    public SnakeController(SnakeModel model) {
        this.model = model;
    }
    public void setSnakeLength(int length){
        model.setLength(length);
    }
    public int getSnakeLength(){
        return model.getLength();
    }
    public void setSnakeScore(int score){
        model.setScore(score);
    }
    public int getSnakeScore(){
        return model.getScore();
    }
    public void addSnakeScore(){
        model.addScore();
    }
    public void setSnakeDead(boolean dead){
        model.setDead(dead);
    }
    public boolean getSnakeDead(){
        return model.getDead();
    }
}
