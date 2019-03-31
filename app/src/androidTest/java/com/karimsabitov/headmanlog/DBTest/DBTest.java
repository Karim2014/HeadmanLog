package com.karimsabitov.headmanlog.DBTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.karimsabitov.headmanlog.schedule.models.ScheduleSingle;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * Тест для БД
 */
@RunWith(AndroidJUnit4.class)
public class DBTest {

    private ScheduleSingle single;

    private final String TAG = "Test";

    @Before
    public void setUp(){
        Context context = InstrumentationRegistry.getTargetContext();
        single = ScheduleSingle.get(context);
    }

   /* @Test
    public void BellsIsNotEmpty(){
        List<Bell> bellsList = single.getBells();
        Assert.assertNotEquals(bellsList.size(), 0);
    }

    @Test
    public void SubjectsIsNotEmpty(){
        List<Subject> subjectList = single.getSubjects();
        Assert.assertNotEquals(subjectList.size(), 0);
    }

    @Test
    public void TeachersIsNotEmpty(){
        List<Teacher> teacherList = single.getTeachers();
        Assert.assertNotEquals(teacherList.size(), 0);
    }

    @Test
    public void CouplesIsNotEmpty() {
        List<Couple> couplesList;
        //Log.d(TAG, "Schedules count: " + single.getSchedules(0).size());
        for (int i = 0; i < 5; i++) {
            couplesList = single.getCouples(1);
            Assert.assertNotEquals(couplesList.size(), 0);
        }
    }*/
}
