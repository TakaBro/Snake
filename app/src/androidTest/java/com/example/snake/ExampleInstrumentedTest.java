package com.example.snake;

import android.content.Context;
import android.view.View;

import com.example.snake.Activities.MainActivity;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>
            (MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void setUp() throws Exception{
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = mActivity.findViewById(R.id.activity_main);
        assertNotNull(view);
    }

    @Test
    public void testDisplayConfig(){
//        SnakeView snakeView;
//        snakeView = new SnakeView(mActivity);
//        snakeView.configureDisplay(mActivity);
//
//        Display display = mActivity.getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        assertEquals(size.x/40, snakeView.blockSize);
//        assertEquals(size.z, );
    }

    @After
    public void tearDown() throws Exception{
        mActivity = null;
    }
}
