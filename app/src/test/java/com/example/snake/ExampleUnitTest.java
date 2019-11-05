package com.example.snake;

import com.example.snake.Controller.SnakeController;
import com.example.snake.Models.SnakeModel;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testScore() throws Exception {
        SnakeModel model;
        SnakeController controller;

        model = new SnakeModel();
        controller = new SnakeController(model);

        for(int i = 0; i < 20; i++)
            controller.addSnakeScore();

        assertEquals(20, controller.getSnakeScore());
    }
}