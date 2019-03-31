package com.karimsabitov.headmanlog.schedule.fragment_activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.karimsabitov.headmanlog.SingleFragmentActivity;

/**
 * Created by User on 12.08.2018.
 */

public class ScheduleEditActivity extends SingleFragmentActivity {

    public static final String DAY_NUM = "DAY_NUM";

    @Override
    protected Fragment createFragment() {
        int dayNum = (int) getIntent().getSerializableExtra(DAY_NUM);
        return ScheduleEditFragment.newInstance(dayNum);
    }

    public static Intent newIntent(Context ctx, int DayNum) {
        Intent intent = new Intent(ctx, ScheduleEditActivity.class);
        intent.putExtra(DAY_NUM, DayNum);
        return intent;
    }
}
