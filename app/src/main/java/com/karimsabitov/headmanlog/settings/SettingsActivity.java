package com.karimsabitov.headmanlog.settings;

import android.support.v4.app.Fragment;

import com.karimsabitov.headmanlog.SingleFragmentActivity;

/**
 * Created by User on 22.08.2018.
 */

public class SettingsActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new SettingsFragment();
    }
}
