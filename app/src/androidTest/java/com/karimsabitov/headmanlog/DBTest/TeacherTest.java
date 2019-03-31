package com.karimsabitov.headmanlog.DBTest;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.karimsabitov.headmanlog.schedule.models.Room;
import com.karimsabitov.headmanlog.schedule.models.ScheduleSingle;
import com.karimsabitov.headmanlog.schedule.models.Teacher;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;
import java.util.UUID;

/**
 * Created by User on 12.01.2019.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeacherTest {

    private String TAG = "Test";
    private ScheduleSingle mSingle;
    private Room[] mRooms = {
            new Room(159),
            new Room(155),
            new Room(157)
    };
    private Teacher[] mTeachers = {
            new Teacher(UUID.fromString("34c81aad-5c59-484c-b26f-e5d48ed199a3"), "Хохлова О.А."),
            new Teacher(UUID.fromString("0528431f-8982-4344-81b5-80dbd34b0e44"), "Терещенко И.С."),
            new Teacher(UUID.fromString("41dd5043-c065-403c-9560-16ae5743aa5a"), "Измалкова Е.Л."),
    };

    @Before
    public void setup() {
        mSingle = ScheduleSingle.get(InstrumentationRegistry.getTargetContext());
    }
    @Test
    public void add() {
        for (int i = 0; i < mRooms.length; i++) {
            long res = mSingle.addRoom(mRooms[i]);
            Log.d(TAG, "add room " + i + ": " + res);
            Assert.assertNotEquals(-1, res);
        }
        for (int i = 0; i < mTeachers.length; i++) {
            long res = mSingle.addTeacher(mTeachers[i]);
            Log.d(TAG, "add teacher: " + i + ": " + res);
            Assert.assertNotEquals(-1, res);
        }
    }

    @Test
    public void get() {
        List<Teacher> teachers = mSingle.getTeachers();
        for (int i = 0; i < teachers.size(); i++) {
            Assert.assertEquals("Проверям на одинаковость", mTeachers[i].getId(), teachers.get(i).getId());
        }
    }

    @Test
    public void h_advancedGet() throws Exception {
        mSingle.deleteRoom(mRooms[1]);

        get();
    }

    @Test
    public void remove() {
        for (int i = 0; i < mRooms.length; i++) {
            long res = mSingle.deleteRoom(mRooms[i]);
            Log.d(TAG, "remove room " + i + ": " + res);
            Assert.assertNotEquals(0, res);
        }
        for (int i = 0; i < mTeachers.length; i++) {
            Log.d(TAG, "remove: подготовка к удалению " + mTeachers[i].toString());
            long res = mSingle.removeTeacher(mTeachers[i]);
            Log.d(TAG, "remove teacher " + i + ": " + res);
            Assert.assertNotEquals(0, res);
        }
    }
}
