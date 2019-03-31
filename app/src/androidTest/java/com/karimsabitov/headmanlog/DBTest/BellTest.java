package com.karimsabitov.headmanlog.DBTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.karimsabitov.headmanlog.schedule.models.Bell;
import com.karimsabitov.headmanlog.schedule.models.ScheduleSingle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Time;
import java.util.Date;

/**
 * Created by User on 04.01.2019.
 */

@RunWith(AndroidJUnit4.class)
public class BellTest {

    private static final String TAG = "Test";
    ScheduleSingle mSingle;
    Bell bell = new Bell(0, new Time(new Date().getTime()), new Time(new Date().getTime())),
            bell1 = new Bell(1, new Time(new Date().getTime()), new Time(new Date().getTime())),
            bell2 = new Bell(2, new Time(new Date().getTime()), new Time(new Date().getTime()));;

    @Before
    public void setup(){
        Context context = InstrumentationRegistry.getTargetContext();
        mSingle = ScheduleSingle.get(context);
    }

    /*@Test
    public void check() {
        addBell();
        getBell();
        updateBell();
        removeBell();
        getBellId();
    }*/

    @Test
    public void addBell(){
        //bell = new Bell(0, new Time(new Date().getTime()), new Time(new Date().getTime()));
        Log.d(TAG, "addBell: " + mSingle.addBell(bell));
        Log.d(TAG, "addBell1: " + mSingle.addBell(bell1));
        Log.d(TAG, "addBell2: " + mSingle.addBell(bell2));
    }

    @Test
    public void getBell() {
        mSingle.getBells();
    }

    @Test
    public void updateBell() {
        bell.setCoupleNum(1);
        Log.d(TAG, "updateBell:" + mSingle.updateBell(bell));
        getBell();
    }

    @Test
    public void removeBell() {
        mSingle.removeBell(bell);
        Log.d(TAG, "removeBell: 0");
        mSingle.removeBell(bell1);
        Log.d(TAG, "removeBell: 1");
        mSingle.removeBell(bell2);
        Log.d(TAG, "removeBell: 2");
        getBell();
    }

    @Test
    public void getBellId(){
        Log.d(TAG, "getBellId: " + mSingle.getBellId(bell));
        Log.d(TAG, "getBellId: " + mSingle.getBellId(bell1));
        Log.d(TAG, "getBellId: " + mSingle.getBellId(bell2));
    }
}
