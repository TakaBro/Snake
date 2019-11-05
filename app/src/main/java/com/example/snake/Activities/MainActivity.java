package com.example.snake.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.example.snake.R;

public class MainActivity extends Activity {

    //comeca partida onClick()
    Intent i;

    Switch gridSwitch, hardSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button startButton = (Button) findViewById(R.id.start);
        gridSwitch = (Switch) findViewById(R.id.switch1);
        hardSwitch = (Switch) findViewById(R.id.switch2);

        startButton.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        i = new Intent(MainActivity.this,GameplayActivity.class);
                        if (gridSwitch.isChecked()){
                            boolean isGridON = gridSwitch.isChecked();
                            i.putExtra("grid", isGridON);
                        }
                        if (hardSwitch.isChecked()){
                            boolean isHard = hardSwitch.isChecked();
                            i.putExtra("hard", isHard);
                        }
                        startActivity(i);
                    }
                }
        );
    }
}
