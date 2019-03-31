package com.karimsabitov.headmanlog.students;

import android.support.v4.app.Fragment;

import com.karimsabitov.headmanlog.SingleFragmentActivity;

/**
 * Created by User on 24.03.2019.
 */

public class StudentAboutActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new StudentsAboutFragment();
    }
}
