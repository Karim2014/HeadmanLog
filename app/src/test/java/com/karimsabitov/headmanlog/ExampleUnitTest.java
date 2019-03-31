package com.karimsabitov.headmanlog;

import android.util.Log;

import org.junit.Test;

import java.sql.Time;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    private static final String TAG = "Test";

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void time_test(){
        Time time = new Time(new Date().getTime());
        Log.d(TAG, time.toString());
    }
}