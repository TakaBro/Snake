package com.example.snake.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;

import com.example.snake.Views.SnakeView;

public class GameplayActivity extends Activity /*implements Runnable*/ {


    SnakeView snakeView;
    boolean playingSnake, isGridON, isHard;
    private int milis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent i = getIntent();
        isGridON = i.getBooleanExtra("grid", isGridON);
        isHard = i.getBooleanExtra("hard", isHard);
        if(isHard){
            milis=50;
        }else{
            milis=150;
        }

        playingSnake = true;

        snakeView = new SnakeView(this);
        snakeView.configDisplay(this, isGridON);
        setContentView(snakeView);

        runThread();
    }

    private void runThread() {

        new Thread() {
            public void run() {
                while (playingSnake) {
                    try {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                snakeView.updateGame();
                                snakeView.draw();
                            }
                        });
                        Thread.sleep(milis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        while (true) {
            playingSnake = false;
            break;
        }
        finish();
    }


   @Override
    protected void onResume() {
        super.onResume();
       playingSnake = true;
    }

     @Override
    protected void onPause() {
        super.onPause();
         playingSnake = false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            onPause();

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        return false;
    }
}

