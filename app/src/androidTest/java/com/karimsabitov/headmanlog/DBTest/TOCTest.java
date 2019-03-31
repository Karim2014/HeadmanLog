package com.karimsabitov.headmanlog.DBTest;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.karimsabitov.headmanlog.schedule.models.ScheduleSingle;
import com.karimsabitov.headmanlog.schedule.models.TOC;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by User on 11.01.2019.
 */

@RunWith(AndroidJUnit4.class)
public class TOCTest {

    ScheduleSingle mSingle;
    String TAG = "Test";
    TOC[] mTOCs = {
            new TOC("Лекция"),
            new TOC("Практическая"),
            new TOC("Семинар"),
            new TOC("Открытое занятие")
    };

    @Before
    public void setup(){
        mSingle = ScheduleSingle.get(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void addTOC(){
        for (int i = 0; i < mTOCs.length; i++) {
            long res = mSingle.addTOC(mTOCs[i]);
            Log.d(TAG, "addTOC " + i + ": " + res);
            Assert.assertNotEquals(-1, res);
        }
        getTOCs();
    }

    @Test
    public void getTOCs(){
        mSingle.getTOCs();
    }

    @Test
    public void updateTOC(){
        int res = mSingle.updateTOC(mTOCs[1], "opop");
        mTOCs[1].setTitle("opop");
        Assert.assertNotEquals(-1, res);
        Log.d(TAG, "updateTOC^ " + res);
        getTOCs();
    }

    @Test
    public void deleteTOCs(){
        for (int i = 0; i < mTOCs.length; i++) {
            long res = mSingle.deleteTOC(mTOCs[i]);
            Log.d(TAG, "addTOC " + i + ": " + res);
            Assert.assertNotEquals(-1, res);
        }
    }
}
