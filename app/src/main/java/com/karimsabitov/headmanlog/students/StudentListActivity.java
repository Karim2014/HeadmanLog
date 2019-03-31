package com.karimsabitov.headmanlog.students;

import android.support.v4.app.Fragment;

import com.karimsabitov.headmanlog.SingleFragmentActivity;

/**
 * Created by User on 01.02.2018.
 */

public class StudentListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new StudentListFragment();
    }
}
