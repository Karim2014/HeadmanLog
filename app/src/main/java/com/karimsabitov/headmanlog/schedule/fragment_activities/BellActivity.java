package com.karimsabitov.headmanlog.schedule.fragment_activities;

import android.support.v4.app.Fragment;

import com.karimsabitov.headmanlog.SingleFragmentActivity;

/**
 * Created by User on 23.03.2019.
 */

public class BellActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return BellFragment.newInstance(false);
    }
}
