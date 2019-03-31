package com.karimsabitov.headmanlog.DBTest;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.karimsabitov.headmanlog.schedule.models.Room;
import com.karimsabitov.headmanlog.schedule.models.ScheduleSingle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by User on 06.01.2019.
 */
@RunWith(AndroidJUnit4.class)
public class RoomTest {

    ScheduleSingle mSingle;
    String TAG = "Test";
    Room[] mRoom = { new Room(159),
            new Room(155),
            new Room(157),
            new Room(123),
            new Room(106)};

    @Before
    public void setUp() throws Exception {
        mSingle = ScheduleSingle.get(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void addRoom(){
        for (int i = 0; i < mRoom.length; i++) {
            long res = mSingle.addRoom(mRoom[i]);
            Log.d(TAG, "addRoom: " + String.format("%1$d: %2$d", i,res ).toString());
            Assert.assertNotEquals(-1, res);
        }
        getRooms();
    }

    /*@Test
    public void updateRoom(){
        for (int i = 0; i < mRoom.length; i++) {
            mRoom[i].setNumber(i + 100);
            Log.d(TAG, "updateRoom: " + String.format("%1$d: %2$d", i, mSingle.updateRoom(mRoom[i])).toString());
        }
        getRooms();
    }*/

    @Test
    public void getById() throws Exception {
        Log.d(TAG, "getById: " + mSingle.getRoomId(new Room(155)));

    }

    @Test
    public void getRooms(){
        mSingle.getRooms();
    }

    @Test
    public void deleteRoom() {
        for (int i = 0; i < mRoom.length; i++) {
            long res = mSingle.deleteRoom(mRoom[i]);
            Log.d(TAG, "deleteRoom: " + String.format("%1$d: %2$d", i, res).toString());
            Assert.assertNotEquals(-1, res);
        }
    }


}