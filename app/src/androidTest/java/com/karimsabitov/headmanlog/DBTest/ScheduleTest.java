package com.karimsabitov.headmanlog.DBTest;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.karimsabitov.headmanlog.schedule.models.Bell;
import com.karimsabitov.headmanlog.schedule.models.Room;
import com.karimsabitov.headmanlog.schedule.models.Schedule;
import com.karimsabitov.headmanlog.schedule.models.ScheduleSingle;
import com.karimsabitov.headmanlog.schedule.models.Subject;
import com.karimsabitov.headmanlog.schedule.models.TOC;
import com.karimsabitov.headmanlog.schedule.models.Teacher;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by User on 13.01.2019.
 */
@RunWith(AndroidJUnit4.class)
public class ScheduleTest {

    ScheduleSingle mSingle;
    private Bell[] mBells;
    private Room[] mRooms;
    private Teacher[] mTeachers;
    private TOC[] mTOCs;
    private Subject[] mSubjects;
    private static final String TAG = "Test";

    @Before
    public void setUp() throws Exception {
        mSingle = ScheduleSingle.get(InstrumentationRegistry.getTargetContext());
        mBells = new Bell[]{
                new Bell(0, new Time(new Date().getTime()), new Time(new Date().getTime())),
                new Bell(1, new Time(new Date().getTime()), new Time(new Date().getTime())),
                new Bell(2, new Time(new Date().getTime()), new Time(new Date().getTime()))};

        mRooms = new Room[]{ new Room(159),
                new Room(155),
                new Room(157),
                new Room(123),
                new Room(106)};

        mTeachers = new Teacher[]{
                new Teacher(UUID.fromString("34c81aad-5c59-484c-b26f-e5d48ed199a3"), "Хохлова О.А."),
                new Teacher(UUID.fromString("0528431f-8982-4344-81b5-80dbd34b0e44"), "Терещенко И.С."),
                new Teacher(UUID.fromString("41dd5043-c065-403c-9560-16ae5743aa5a"), "Измалкова Е.Л."),};

        mTOCs = new TOC[]{
                new TOC("Лекция"),
                new TOC("Практическая"),
                new TOC("Семинар"),
                new TOC("Открытое занятие")};

        mSubjects = new Subject[]{
                new Subject("Док"),
                new Subject("БД"),
                new Subject("ТРПО"),
                new Subject("ИС"),};

        for (int i = 0; i < mBells.length; i++) {
            mSingle.addBell(mBells[i]);
            mSingle.addTeacher(mTeachers[i]);
        }
        for (int i = 0; i < 4; i++) {
            mSingle.addRoom(mRooms[i]);
            mSingle.addTOC(mTOCs[i]);
            mSingle.addSubject(mSubjects[i]);
        }
    }

    @Test
    public void main() /*throws Exception*/ {

        Schedule schedule = new Schedule(
                mSubjects[0],
                mTeachers[0],
                mRooms[0],
                mBells[0],
                mTOCs[0],
                false, false, 0
        );

        long res = mSingle.addSchedule(schedule);
        Log.d(TAG, "addSch " + res);
        Assert.assertNotEquals(-1, res);

        List<Schedule> scheduleList = mSingle.getSchedules(0, false);
        Assert.assertNotEquals(0, scheduleList.size());
        Log.d(TAG, "sch1: " + scheduleList.get(0).toString());

        res = mSingle.removeSchedule(schedule);
        Log.d(TAG, "delSch " + res);
        Assert.assertNotEquals(0, res);

    }

    @After
    public void tearDown() throws Exception {
        for (int i = 0; i < mBells.length; i++) {
            mSingle.removeBell(mBells[i]);
            mSingle.removeTeacher(mTeachers[i]);
        }
        for (int i = 0; i < 4; i++) {
            mSingle.deleteRoom(mRooms[i]);
            mSingle.deleteTOC(mTOCs[i]);
            mSingle.removeSubject(mSubjects[i]);
        }
    }
}
