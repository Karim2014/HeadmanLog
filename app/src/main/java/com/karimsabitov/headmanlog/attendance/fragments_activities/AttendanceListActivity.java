package com.karimsabitov.headmanlog.attendance.fragments_activities;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.karimsabitov.headmanlog.SingleFragmentActivity;

/**
 * Created by User on 26.09.2018.
 */

public class AttendanceListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return AttendanceListFragment.newInstance();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
